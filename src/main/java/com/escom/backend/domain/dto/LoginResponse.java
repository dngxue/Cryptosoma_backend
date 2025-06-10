package com.escom.backend.domain.dto;

import com.escom.backend.domain.entities.Rol;

public class LoginResponse {
  public String nombre;
  public String email;
  public Rol rol;
  public String token;  

  public LoginResponse(String nombre, String email, Rol rol, String token) {
    this.nombre = nombre;
    this.email = email;
    this.rol = rol;
    this.token = token;
  }
}
