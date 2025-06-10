package com.escom.backend.utils;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.escom.backend.domain.repositories.PacienteRepository;

@Component
public class MatriculaUtil {
  private static final Random rdm = new Random();
  public String generarMatricula(PacienteRepository pacienteRepository) {
    String matricula;

    do {
      String año = String.valueOf(LocalDate.now().getYear());
      int secuence = (int) rdm.nextInt(999999) + 100000;
      matricula = año + String.valueOf(secuence);
    } while (pacienteRepository.existsByMatricula(matricula));

    return matricula;
  } 
}
