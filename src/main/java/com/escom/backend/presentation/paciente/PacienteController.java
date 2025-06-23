package com.escom.backend.presentation.paciente;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.prescription.CreateAccessKeyPharmacistDTO;
import com.escom.backend.presentation.securityJWT.JwtSessionInfo;
import com.escom.backend.presentation.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PacienteController {
  @Autowired PatientService patientService;

  @PostMapping("/grant-access")
  public ResponseEntity<?> grantAccess(@RequestBody CreateAccessKeyPharmacistDTO dto) {
    UUID usuarioId = JwtSessionInfo.getUserId();
    return ResponseEntity.ok(patientService.grantAccessToPharmacist(dto, usuarioId));
  }
}
