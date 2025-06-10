package com.escom.backend.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escom.backend.domain.entities.Farmaceutico;

public interface FarmaceuticoRepository extends JpaRepository<Farmaceutico, Long> {
  
}
