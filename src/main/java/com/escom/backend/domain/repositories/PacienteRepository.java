package com.escom.backend.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.users.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {  
  boolean existsByMatricula(String matricula);
  Optional<Paciente> findByMatricula(String matricula);
}
