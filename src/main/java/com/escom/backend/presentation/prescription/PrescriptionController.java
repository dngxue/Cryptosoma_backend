package com.escom.backend.presentation.prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.escom.backend.domain.dto.PrescriptionDTO;
import com.escom.backend.presentation.services.PrescriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

  @Autowired
  private PrescriptionService prescriptionService;

  @PostMapping(value = "/save", consumes = "multipart/form-data")
  public ResponseEntity<String> createPrescription(
      @RequestPart("data") @Valid PrescriptionDTO dto,
      @RequestPart("file") MultipartFile file
  ) {
    return ResponseEntity.ok(prescriptionService.savePrescription(dto));
  }
}
