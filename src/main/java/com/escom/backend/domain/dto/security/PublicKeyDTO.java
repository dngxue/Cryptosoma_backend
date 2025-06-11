package com.escom.backend.domain.dto.security;

import java.util.UUID;

import com.escom.backend.domain.entities.security.KeyType;

import jakarta.validation.constraints.NotBlank;

public class PublicKeyDTO {
  @NotBlank(message = "El ID del usuario es obligatorio")
  public UUID usuario_id;

  @NotBlank(message = "El tipo de clave es obligatorio")
  public KeyType keyType;

  @NotBlank(message = "La clave pública es obligatoria")
  public String publicKey;
}
