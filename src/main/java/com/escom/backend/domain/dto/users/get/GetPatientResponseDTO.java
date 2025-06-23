package com.escom.backend.domain.dto.users.get;

import java.time.LocalDate;
import java.util.UUID;

public record GetPatientResponseDTO(
  UUID id,
  String matricula,
  String curp,
  String name,
  LocalDate fechaNacimiento
) {
}
