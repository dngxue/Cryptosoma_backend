package com.escom.backend.presentation.prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.escom.backend.domain.dto.prescription.PrescriptionDTO;
import com.escom.backend.presentation.services.PrescriptionService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

  @Autowired
  private PrescriptionService prescriptionService;

  @PostConstruct
  public void init() {
      System.out.println("✅ PrescriptionController registrado correctamente");
  }


  @PostMapping("/save")
  public ResponseEntity<String> createPrescription(@Valid @RequestBody PrescriptionDTO dto) {
    return ResponseEntity.ok(prescriptionService.savePrescription(dto));
    // return ResponseEntity.ok("Receta creada y cifrada con éxito.");
  }
}
