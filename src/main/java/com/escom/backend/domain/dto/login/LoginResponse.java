package com.escom.backend.domain.dto.login;

import java.util.UUID;

import com.escom.backend.domain.entities.users.Rol;

public class LoginResponse {
  public UUID userId;
  public String nombre;
  public String email;
  public Rol rol;
  public Boolean keys;
  public String token;  

  public LoginResponse(UUID userId, String nombre, String email, Rol rol, Boolean keys, String token) {
    this.userId = userId; 
    this.nombre = nombre;
    this.email = email;
    this.rol = rol;
    this.keys = keys;
    this.token = token;
  }
}
