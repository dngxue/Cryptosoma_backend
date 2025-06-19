package com.escom.backend.domain.dto.prescription;

import java.time.LocalDate;
import java.util.UUID;

public record PrescriptionResponseDTO(
  UUID id,
  LocalDate fechaEmision,
  String clinica
) {
}