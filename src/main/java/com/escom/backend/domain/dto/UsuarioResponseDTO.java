package com.escom.backend.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponseDTO(
  UUID id,
  String matricula,
  String curp,
  String name,
  LocalDate fechaNacimiento
) {
}
