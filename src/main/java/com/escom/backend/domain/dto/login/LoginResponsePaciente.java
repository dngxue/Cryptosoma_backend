package com.escom.backend.domain.dto.login;

import com.escom.backend.domain.entities.users.Usuario;

public class LoginResponsePaciente extends LoginResponse {
  public String curp;
  public String matricula;

  public LoginResponsePaciente(Usuario user, String token, String curp, String matricula) {
    super(user.getId(), user.getNombre(), user.getEmail(), user.getRol(), user.getKeys(), token);
    this.curp = curp;
    this.matricula = matricula;
  }
}
