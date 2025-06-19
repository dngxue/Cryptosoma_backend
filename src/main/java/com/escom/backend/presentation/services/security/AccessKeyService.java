package com.escom.backend.presentation.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.security.AccessKey;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.AccessKeyRepository;

@Service
public class AccessKeyService {
  @Autowired private AccessKeyRepository accessKeyRepository;
  
  public void createAccessKey(Usuario usuario, Prescription prescription, String encryptedKey, String publicKeyServerB64) {
    AccessKey accessKey = new AccessKey();
    accessKey.setUsuario(usuario);
    accessKey.setPrescription(prescription);
    accessKey.setServerPublicKey(publicKeyServerB64);
    accessKey.setKey(encryptedKey);
    accessKeyRepository.save(accessKey);
  }
}
