package com.escom.backend.presentation.prescription;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.prescription.CreatePrescriptionDTO;
import com.escom.backend.presentation.securityJWT.JwtSessionInfo;
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
  public ResponseEntity<?> createPrescription(@Valid @RequestBody CreatePrescriptionDTO dto) {
    Map<String, Object> result = prescriptionService.savePrescription(dto);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getPrescriptionUser(@PathVariable("userId") UUID userId) {
    return ResponseEntity.ok(prescriptionService.getPrescriptionsByUser(userId));
  }

  @GetMapping("/encrypted/{recetaId}")
  public ResponseEntity<?> getAccessKeyForUser(@PathVariable UUID recetaId) {
      UUID usuarioId = JwtSessionInfo.getUserId();
      return ResponseEntity.ok(prescriptionService.getEncryptedPrescription(usuarioId, recetaId));
  }
}
