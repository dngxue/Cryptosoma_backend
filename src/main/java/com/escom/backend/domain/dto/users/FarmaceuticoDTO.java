package com.escom.backend.domain.dto.users;

public record FarmaceuticoDTO(
  UsuarioDTO usuario,
  String farmacia,
  String telefono
) {}
