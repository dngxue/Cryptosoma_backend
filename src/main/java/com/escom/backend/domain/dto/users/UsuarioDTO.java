package com.escom.backend.domain.dto.users;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioDTO(
  UUID id,
  String email,
  String nombre,
  LocalDate fechaNacimiento
) {}