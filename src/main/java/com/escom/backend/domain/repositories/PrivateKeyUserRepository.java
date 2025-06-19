package com.escom.backend.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.security.PrivateKeyUser;

public interface PrivateKeyUserRepository extends JpaRepository<PrivateKeyUser, UUID> {
  Optional<PrivateKeyUser> findByUsuario_Id(UUID usuarioId);
}
