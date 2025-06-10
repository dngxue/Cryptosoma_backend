package com.escom.backend.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {  
  boolean existsByMatricula(String matricula);
}
