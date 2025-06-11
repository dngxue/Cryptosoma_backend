package com.escom.backend.presentation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.PrescriptionDTO;
import com.escom.backend.domain.repositories.PrescriptionRepository;

@Service
public class PrescriptionService {
  @Autowired
  private PrescriptionRepository prescriptionRepository;
  
  public String savePrescription(PrescriptionDTO prescriptionDTO) {
    
    return "Receta guardada con éxito";
  }
}
