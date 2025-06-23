package com.escom.backend.domain.dto.users.get;

import java.util.UUID;

public record GetPharmacist(
  UUID id,
  String name,
  String farmacia,
  String telefono
) {}
