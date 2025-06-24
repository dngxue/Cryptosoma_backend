package com.escom.backend.domain.dto.login;

import com.escom.backend.domain.entities.users.Usuario;

public class LoginResponseFarmaceutico extends LoginResponse {
  public String telefono;
  public String farmacia;

  public LoginResponseFarmaceutico(Usuario user, String token, String telefono, String farmacia) {
    super(user.getId(), user.getNombre(), user.getEmail(), user.getRol(), user.getKeys(), token);
    this.telefono = telefono;
    this.farmacia = farmacia;
  }
}
