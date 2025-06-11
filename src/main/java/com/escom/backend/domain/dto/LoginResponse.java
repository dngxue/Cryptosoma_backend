package com.escom.backend.domain.dto;

import java.util.UUID;

import com.escom.backend.domain.entities.Rol;

public class LoginResponse {
  public UUID userId;
  public String nombre;
  public String email;
  public Rol rol;
  public String token;  

  public LoginResponse(UUID userId, String nombre, String email, Rol rol, String token) {
    this.userId = userId; 
    this.nombre = nombre;
    this.email = email;
    this.rol = rol;
    this.token = token;
  }
}
