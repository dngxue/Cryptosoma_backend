package com.escom.backend.domain.dto.prescription;

import java.time.LocalDate;
import java.util.UUID;

public record PrescriptionDTO(
  UUID id,
  Boolean surtida,
  String firma_medico,
  LocalDate fecha_emision,
  String firma_farmaceutico,
  LocalDate fecha_surtido
) {} 
