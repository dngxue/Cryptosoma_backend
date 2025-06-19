package com.escom.backend.domain.dto.users.get;

import java.util.UUID;

public record PharmacistResponseDTO(
  UUID id,
  String name,
  String farmacia,
  String telefono
) {}
