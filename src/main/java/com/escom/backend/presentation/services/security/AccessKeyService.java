package com.escom.backend.presentation.services.security;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.prescription.PrescriptionResponseDTO;
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

  public List<PrescriptionResponseDTO> getPrescriptionsByUserAndAccessKey(UUID userId) {
    return accessKeyRepository.findByUsuario_Id(userId).stream()
      .map(ak -> {
        Prescription p = ak.getPrescription();
        return new PrescriptionResponseDTO(
          p.getId(),
          p.getFecha_emision(),
          p.getMedico().getClinica()
        );
      })
      .collect(Collectors.toList());
  }
}
