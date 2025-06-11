package com.escom.backend.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.security.PublicKeyUser;

public interface PublicKeyRepository extends JpaRepository<PublicKeyUser, UUID>{
  
}
