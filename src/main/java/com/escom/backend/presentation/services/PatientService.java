package com.escom.backend.presentation.services;


import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.prescription.CreateAccessKeyPharmacistDTO;
import com.escom.backend.domain.dto.shared.ResponseDTO;
import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.security.AccessKey;
import com.escom.backend.domain.entities.security.KeyType;
import com.escom.backend.domain.entities.security.PrivateKeyUser;
import com.escom.backend.domain.entities.security.PublicKeyUser;
import com.escom.backend.domain.entities.users.Farmaceutico;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.AccessKeyRepository;
import com.escom.backend.domain.repositories.FarmaceuticoRepository;
import com.escom.backend.domain.repositories.PrescriptionRepository;
import com.escom.backend.domain.repositories.PrivateKeyUserRepository;
import com.escom.backend.domain.repositories.PublicKeyUserRepository;
import com.escom.backend.domain.repositories.UsuarioRepository;
import com.escom.backend.presentation.cripto.AES_GCM;
import com.escom.backend.presentation.cripto.ECDH25519;
import com.escom.backend.presentation.services.security.AccessKeyService;
import com.escom.backend.presentation.services.security.KeyAgreementService;
import com.escom.backend.presentation.services.security.KeyAgreementService.KeyAgreementResult;

@Service
public class PatientService {
  @Autowired private PrescriptionRepository prescriptionRepository;
  @Autowired private FarmaceuticoRepository farmaceuticoRepository;
  @Autowired private PublicKeyUserRepository publicKeyUserRepository;
  @Autowired private PrivateKeyUserRepository privateKeyUserRepository;
  @Autowired private AccessKeyRepository accessKeyRepository;
  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private KeyAgreementService keyAgreementService;
  @Autowired private AccessKeyService accessKeyService;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public ResponseDTO grantAccessToPharmacist(CreateAccessKeyPharmacistDTO accessPharmacistDTO, UUID usuarioId) {
    Usuario user = usuarioRepository.findById(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

    if(!encoder.matches(accessPharmacistDTO.password(), user.getPassword())) {
      throw new RuntimeException("La contraseña no coincide");
    }

    Prescription prescription = prescriptionRepository.findById(accessPharmacistDTO.idReceta())
      .orElseThrow(() -> new RuntimeException("Receta médica no encontrada"));

    Farmaceutico farmaceutico = farmaceuticoRepository.findById(accessPharmacistDTO.idFarmaceutico())
      .orElseThrow(() -> new RuntimeException("Receta médica no encontrada"));

    PublicKeyUser publicKeyPharmacist = publicKeyUserRepository.findByUsuario_IdAndKeyType(usuarioId, KeyType.ECDH)
      .orElseThrow(() -> new RuntimeException("Llave pública de usuario no encontrada"));

    PublicKeyUser publicKeyPatient = publicKeyUserRepository.findByUsuario_IdAndKeyType(usuarioId, KeyType.ECDH)
      .orElseThrow(() -> new RuntimeException("Llave públic del paciente no encontrada"));

    PrivateKeyUser privateKeyUser = privateKeyUserRepository.findByUsuario_Id(usuarioId)
      .orElseThrow(() -> new RuntimeException("Llave privada del usuario no encontrada"));

    AccessKey accessKey = accessKeyRepository.findByUsuario_IdAndPrescription_Id(usuarioId, accessPharmacistDTO.idReceta())
      .orElseThrow(() -> new RuntimeException("Llave de acceso del usuario " + usuarioId + " no encontrada"));
    
    
    char[] passwordChar = accessPharmacistDTO.password().toCharArray(); 
    byte[] decodedEncryptedPrivateKey = Base64.getDecoder().decode(privateKeyUser.getEncryptedKey());
    byte[] publicKeyPharmacistBytes = Base64.getDecoder().decode(publicKeyPharmacist.getPublicKey());
    byte[] accessKeyBytes = Base64.getDecoder().decode(accessKey.getKey());
    byte[] serverPublicKey = Base64.getDecoder().decode(accessKey.getServerPublicKey());

    try {
      byte[] decryptedPrivateKey = decryptPrivateKey(passwordChar, decodedEncryptedPrivateKey);
      System.out.println("Llave privada: " + Base64.getEncoder().encodeToString(decryptedPrivateKey));
      byte[] sharedKey = ECDH25519.deriveSharedSecret(decryptedPrivateKey, serverPublicKey);
      byte[] aesKey = AES_GCM.decryptGCM(accessKeyBytes, sharedKey);
      System.out.println("Llave de AES con la que se cifró el archivo: " + Base64.getEncoder().encodeToString(aesKey));      

      byte[] sharedKeyPhatmacistAndPatient = ECDH25519.deriveSharedSecret(decryptedPrivateKey, publicKeyPharmacistBytes);
      String encryptedAESKey = AES_GCM.encryptGCM(aesKey, sharedKeyPhatmacistAndPatient);
      accessKeyService.createAccessKey(
        farmaceutico.getUsuario(), 
        prescription, 
        encryptedAESKey, 
        publicKeyPatient.getPublicKey());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return new ResponseDTO("success", "Se ha creado una llave de acceso para el farmacéutico: " + farmaceutico.getId());
  }

  private byte[] decryptPrivateKey(char[] password, byte[] encryptedPrivateKeyB64) throws Exception {
    byte[] salt = Arrays.copyOfRange(encryptedPrivateKeyB64, 0, 16);
    byte[] iv = Arrays.copyOfRange(encryptedPrivateKeyB64, 16, 28);
    byte[] privateKeyDecoded = Arrays.copyOfRange(encryptedPrivateKeyB64, 28, encryptedPrivateKeyB64.length);
    SecretKey secretKey = AES_GCM.deriveAESKeyFromPassword(password, salt);
    byte[] decryptedPrivateKey = AES_GCM.decryptWithAESGCM(privateKeyDecoded, iv, secretKey);
    return decryptedPrivateKey;
  }

}
