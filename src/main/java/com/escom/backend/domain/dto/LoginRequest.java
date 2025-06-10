package com.escom.backend.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  @Email(message = "El correo no tiene un formato válido")
  @NotBlank(message = "El correo no puede estar vacío")
  public String email;

  @NotBlank(message = "La contraseña es obligatoria")
  public String password;
}