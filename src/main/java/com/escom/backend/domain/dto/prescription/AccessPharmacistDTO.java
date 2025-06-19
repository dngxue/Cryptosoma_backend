package com.escom.backend.domain.dto.prescription;

import java.util.UUID;

public record AccessPharmacistDTO (
  UUID idReceta,
  UUID idFarmaceutico,
  String encryptedKey,
  String publicKeyPaciente
) {
}
