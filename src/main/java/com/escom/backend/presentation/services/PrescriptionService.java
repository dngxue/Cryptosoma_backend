package com.escom.backend.presentation.services;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.prescription.PrescriptionDTO;
import com.escom.backend.domain.dto.prescription.PrescriptionResponseDTO;
import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.security.KeyType;
import com.escom.backend.domain.entities.security.PublicKeyUser;
import com.escom.backend.domain.entities.users.Medico;
import com.escom.backend.domain.entities.users.Paciente;
import com.escom.backend.domain.repositories.MedicoRepository;
import com.escom.backend.domain.repositories.PacienteRepository;
import com.escom.backend.domain.repositories.PrescriptionRepository;
import com.escom.backend.domain.repositories.PublicKeyUserRepository;
import com.escom.backend.presentation.cripto.AES_GCM;
import com.escom.backend.presentation.services.security.AccessKeyService;
import com.escom.backend.presentation.services.security.KeyAgreementService;
import com.escom.backend.presentation.services.security.SignatureService;
import com.escom.backend.presentation.services.security.KeyAgreementService.KeyAgreementResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class PrescriptionService {
  @Autowired private PrescriptionRepository prescriptionRepository;
  @Autowired private PacienteRepository pacienteRepository;
  @Autowired private MedicoRepository medicoRepository;
  @Autowired private PublicKeyUserRepository publicKeyUserRepository;

  @Autowired private SignatureService signatureService;
  @Autowired private KeyAgreementService keyAgreementService;
  @Autowired private AccessKeyService accessKeyService;

  public Map<String, Object> savePrescription(PrescriptionDTO prescriptionDTO) {
    Paciente paciente = pacienteRepository.findById(prescriptionDTO.id_paciente)
    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + prescriptionDTO.id_paciente));
    
    Medico medico = medicoRepository.findById(prescriptionDTO.id_medico)
    .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + prescriptionDTO.id_medico));

    PublicKeyUser publicKeyMedicoEdDSA = publicKeyUserRepository.findByUsuario_IdAndKeyType(medico.getUsuario().getId(), KeyType.EdDSA)
    .orElseThrow(() -> new RuntimeException("Clave pública EdDSA no encontrada para el médico con ID: " + medico.getUsuario().getId()));

    ObjectMapper mapper = createObjectMapper();
    ObjectNode rootNode = mapper.valueToTree(prescriptionDTO);
    String firma = rootNode.remove("firma_medico").asText();
    String cleanJson;

    try {
        cleanJson = mapper.writeValueAsString(rootNode);
    } catch (JsonProcessingException e) {
        throw new RuntimeException("Error al convertir la receta a JSON: " + e.getMessage());
    }
    byte[] message = cleanJson.getBytes(StandardCharsets.UTF_8);
    signatureService.verifySignature(message, firma, publicKeyMedicoEdDSA.getPublicKey());

    // Registrar la receta.
    Prescription prescription = new Prescription();
    prescription.setPaciente(paciente);
    prescription.setFilename("name_temp");
    prescription.setMedico(medico);
    prescription.setSignatureMedico(firma);
    prescription.setFecha_emision(prescriptionDTO.fecha_emision);
    prescription = prescriptionRepository.save(prescription);

    PublicKeyUser publicKeyPaciente = publicKeyUserRepository.findByUsuario_IdAndKeyType(paciente.getUsuario().getId(), KeyType.ECDH)
    .orElseThrow(() -> new RuntimeException("Clave pública ECDH no encontrada para el paciente: " + paciente.getUsuario().getId()));
    
    PublicKeyUser publicKeyMedico = publicKeyUserRepository.findByUsuario_IdAndKeyType(medico.getUsuario().getId(), KeyType.ECDH)
      .orElseThrow(() -> new RuntimeException("Clave públic ECDH no encontrada para el médico: " + medico.getUsuario().getId()));

    try {
      String filename = prescription.getId().toString() + ".enc";
      SecretKey aesSecretKey = AES_GCM.generateAESKey(256);
      byte[] aesKeyBytes = aesSecretKey.getEncoded();

      String encodedFile = AES_GCM.encryptGCM(aesKeyBytes, message);
      saveEncryptedPrescription(prescription, encodedFile, filename);
      
      KeyAgreementResult resultPaciente = keyAgreementService.generateSharedKey(publicKeyPaciente.getPublicKey());
      String encodedKeyPaciente = AES_GCM.encryptGCM(resultPaciente.sharedKey(), aesKeyBytes);
      accessKeyService.createAccessKey(paciente.getUsuario(), prescription, encodedKeyPaciente, resultPaciente.serverKeyPair().getPublicKeyBase64());
      
      KeyAgreementResult resultMedico = keyAgreementService.generateSharedKey(publicKeyMedico.getPublicKey());
      String encodedKeyMedico = AES_GCM.encryptGCM(resultMedico.sharedKey(), aesKeyBytes);
      accessKeyService.createAccessKey(medico.getUsuario(), prescription, encodedKeyMedico, resultMedico.serverKeyPair().getPublicKeyBase64());
    } catch (Exception e) {
      throw new RuntimeException("Error al generar la llave compartida" + paciente.getUsuario().getId());
    }

    Map<String, Object> response = Map.of(
      "message", "Receta verificada y almacenada correctamente"
    );

    return response;
  }

  public byte[] getEncyptedPrescription(UUID prescriptionId) {
    Prescription prescription = prescriptionRepository.findById(prescriptionId)
        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

    Path path = Paths.get("prescriptions", prescription.getFilename());
    if (!Files.exists(path)) {
        throw new RuntimeException("Archivo cifrado no encontrado para la receta con ID: " + prescriptionId);
    }
    try {
        return Files.readAllBytes(path); 
    } catch (Exception e) {
        throw new RuntimeException("Error al leer el archivo cifrado: " + e.getMessage());
    }

  }

  public List<PrescriptionResponseDTO> getPrescriptionsByUser(UUID userId) {
    return accessKeyService.getPrescriptionsByUserAndAccessKey(userId);
  }

  private ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.registerModule(new JavaTimeModule()); // Para manejar fechas y horas
    return mapper;
  }

  private void saveEncryptedPrescription (Prescription prescription, String encodedFile, String filename) {
    try {
      Path path = Paths.get("prescriptions", filename);
      Files.createDirectories(path.getParent());
      Files.writeString(path, encodedFile, StandardCharsets.UTF_8);
      prescription.setFilename(filename);
      prescriptionRepository.save(prescription);
      System.out.println("Archivo cifrado y almacenado correctamente");
    } catch (Exception e) {
        throw new RuntimeException("Error al guardar la receta en el sistema de archivos: " + e.getMessage());
    }
  }
}
