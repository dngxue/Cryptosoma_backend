package com.escom.backend.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.security.KeyType;
import com.escom.backend.domain.entities.security.PublicKeyUser;

public interface PublicKeyUserRepository extends JpaRepository<PublicKeyUser, UUID>{
  Optional<PublicKeyUser> findByUsuario_IdAndKeyType(UUID usuarioId, KeyType keyType);
}
