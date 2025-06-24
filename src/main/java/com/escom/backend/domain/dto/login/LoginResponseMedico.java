package com.escom.backend.domain.dto.login;

import com.escom.backend.domain.entities.users.Usuario;

public class LoginResponseMedico extends LoginResponse {
  public String cedula;
  public String especialidad;
  public String clinica;
  public String telefono;

  public LoginResponseMedico(Usuario user, String token, String cedula, String especialidad, String clinica, String telefono) {
    super(user.getId(), user.getNombre(), user.getEmail(), user.getRol(), user.getKeys(), token);
    this.cedula = cedula;
    this.especialidad = especialidad;
    this.clinica = clinica;
    this.telefono = telefono;
  }
}
