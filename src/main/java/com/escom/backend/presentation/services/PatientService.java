package com.escom.backend.presentation.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.prescription.CreateAccessKeyPharmacistDTO;
import com.escom.backend.domain.dto.shared.ResponseDTO;
import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.users.Farmaceutico;
import com.escom.backend.domain.repositories.FarmaceuticoRepository;
import com.escom.backend.domain.repositories.PrescriptionRepository;
import com.escom.backend.presentation.services.security.AccessKeyService;

@Service
public class PatientService {
  @Autowired private PrescriptionRepository prescriptionRepository;
  @Autowired private FarmaceuticoRepository farmaceuticoRepository;
  @Autowired private AccessKeyService accessKeyService;

  public ResponseDTO grantAccessToPharmacist(CreateAccessKeyPharmacistDTO accessPharmacistDTO) {
    Prescription prescription = prescriptionRepository.findById(accessPharmacistDTO.idReceta())
      .orElseThrow(() -> new RuntimeException("Receta médica no encontrada"));

    Farmaceutico farmaceutico = farmaceuticoRepository.findById(accessPharmacistDTO.idFarmaceutico())
      .orElseThrow(() -> new RuntimeException("Receta médica no encontrada"));

    accessKeyService.createAccessKey(farmaceutico.getUsuario(), prescription, accessPharmacistDTO.encryptedKey(), accessPharmacistDTO.publicKeyPaciente()); 
      return new ResponseDTO("success", "Se ha cedido el acceso al farmacéutico " + farmaceutico.getId());
  }
}
