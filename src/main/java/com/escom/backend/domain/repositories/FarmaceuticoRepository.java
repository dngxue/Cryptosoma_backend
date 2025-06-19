package com.escom.backend.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.users.Farmaceutico;

public interface FarmaceuticoRepository extends JpaRepository<Farmaceutico, Long> {
  
}
