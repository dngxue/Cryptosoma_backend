package com.escom.backend.domain.dto.users;

public record MedicoDTO (
  UsuarioDTO usuario,
  String especialidad, 
  String clinica,
  String cedula,
  String telefono
) {}
