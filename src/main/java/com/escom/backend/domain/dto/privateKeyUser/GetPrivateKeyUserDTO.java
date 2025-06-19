package com.escom.backend.domain.dto.privateKeyUser;

public record GetPrivateKeyUserDTO(
  String message,
  String status,
  String encryptedKey
) {
} 
