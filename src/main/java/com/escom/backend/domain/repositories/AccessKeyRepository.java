package com.escom.backend.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.security.AccessKey;

public interface AccessKeyRepository extends JpaRepository<AccessKey, UUID>{
  List<AccessKey> findByUsuario_Id(UUID userId);
  Optional<AccessKey> findByUsuario_IdAndPrescription_Id(UUID usuarioId, UUID prescriptionId);
}
