package com.escom.backend.domain.dto;

import java.time.LocalDate;

public record UsuarioResponseDTO(
  String matricula,
  String curp,
  String name,
  LocalDate fechaNacimiento
) {
}
