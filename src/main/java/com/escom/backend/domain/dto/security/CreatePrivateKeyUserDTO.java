package com.escom.backend.domain.dto.security;

import java.util.UUID;

public record CreatePrivateKeyUserDTO(
  UUID idUser,
  String encryptedKey
) {
} 
