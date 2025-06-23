package com.escom.backend.domain.dto.prescription;

import com.escom.backend.domain.dto.users.PacienteDTO;
import com.escom.backend.domain.dto.users.MedicoDTO;
public record GetEncryptedPrescriptionDTO(
  String accessKey,
  String publicKeyServidor,
  String encryptedPrescription,
  PacienteDTO paciente,
  MedicoDTO medico,
  PrescriptionDTO prescription
) {} 
