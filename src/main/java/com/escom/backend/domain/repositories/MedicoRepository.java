package com.escom.backend.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.users.Medico;

public interface MedicoRepository extends JpaRepository<Medico, UUID> {
}