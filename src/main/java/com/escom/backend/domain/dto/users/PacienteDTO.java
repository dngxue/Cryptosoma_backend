package com.escom.backend.domain.dto.users;

public record PacienteDTO (
  UsuarioDTO usuario,
  String matricula,
  String curp
) {}


