package com.escom.backend.presentation.services;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.prescription.PrescriptionDTO;
import com.escom.backend.domain.entities.Medico;
import com.escom.backend.domain.entities.Paciente;
import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.security.KeyType;
import com.escom.backend.domain.entities.security.PublicKeyUser;
import com.escom.backend.domain.repositories.MedicoRepository;
import com.escom.backend.domain.repositories.PacienteRepository;
import com.escom.backend.domain.repositories.PrescriptionRepository;
import com.escom.backend.domain.repositories.PublicKeyUserRepository;
import com.escom.backend.presentation.cripto.EdDSA25519;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class PrescriptionService {
  @Autowired
  private PrescriptionRepository prescriptionRepository;

  @Autowired
  private PacienteRepository pacienteRepository;
  
  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PublicKeyUserRepository publicKeyUserRepository;
  
  public Map<String, Object> savePrescription(PrescriptionDTO prescriptionDTO) {
    Paciente paciente = pacienteRepository.findById(prescriptionDTO.id_paciente)
    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + prescriptionDTO.id_paciente));
    
    Medico medico = medicoRepository.findById(prescriptionDTO.id_medico)
    .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + prescriptionDTO.id_medico));

    PublicKeyUser publicKeyUser = publicKeyUserRepository.findByUsuario_IdAndKeyType(medico.getUsuario().getId(), KeyType.EdDSA)
    .orElseThrow(() -> new RuntimeException("Clave pública no encontrada para el médico con ID: " + medico.getUsuario().getId()));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.registerModule(new JavaTimeModule()); // Para manejar fechas y horas

    // Serealizar el DTO a un JSON para eliminar la firma del médico
    ObjectNode rootNode = mapper.valueToTree(prescriptionDTO);
    String firma = rootNode.remove("firma_medico").asText();
    String cleanJson;

    try {
        cleanJson = mapper.writeValueAsString(rootNode);
    } catch (JsonProcessingException e) {
        throw new RuntimeException("Error al convertir la receta a JSON: " + e.getMessage());
    }

    byte[] messageBytes = cleanJson.getBytes(StandardCharsets.UTF_8);
    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyUser.getPublicKey());
    byte[] signatureBytes = Base64.getDecoder().decode(firma);

    // Verificar la firma del médico
    if (!EdDSA25519.verifySignature(publicKeyBytes, messageBytes, signatureBytes)) {
      throw new RuntimeException("Firma del médico no válida.");
    }

    // Almacenar la receta.
    Prescription prescription = new Prescription();
    prescription.setPaciente(paciente);
    prescription.setFilename("name_temp");
    prescription.setMedico(medico);
    prescription.setSignatureMedico(firma);
    prescription.setFecha_emision(prescriptionDTO.fecha_emision);
    
    prescription = prescriptionRepository.save(prescription);

    String filename = prescription.getId().toString() + ".enc";
    try {
      Path path = Paths.get("prescriptions", filename);
      Files.createDirectories(path.getParent());
      Files.write(path, cleanJson.getBytes(StandardCharsets.UTF_8));
      prescription.setFilename(filename);
      prescriptionRepository.save(prescription);
    } catch (Exception e) {
        throw new RuntimeException("Error al guardar la receta en el sistema de archivos: " + e.getMessage());
    }

    Map<String, Object> response = Map.of(
      "message", "Receta verificada y almacenada correctamente"
    );

    return response;
  }
}
