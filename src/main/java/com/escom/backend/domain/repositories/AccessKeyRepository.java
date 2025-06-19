package com.escom.backend.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.security.AccessKey;

public interface AccessKeyRepository extends JpaRepository<AccessKey, UUID>{
  
}
