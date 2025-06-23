package com.escom.backend.domain.dto.prescription;

import java.util.UUID;

public record CreateAccessKeyPharmacistDTO (
  UUID idReceta,
  UUID idFarmaceutico,
  String encryptedKey,
  String publicKeyPaciente
) {
}
