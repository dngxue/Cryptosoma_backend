package com.escom.backend.presentation.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.users.get.PharmacistResponseDTO;
import com.escom.backend.domain.entities.users.Farmaceutico;
import com.escom.backend.domain.repositories.FarmaceuticoRepository;
import com.escom.backend.domain.repositories.PacienteRepository;

@Service
public class PharmacistService {
  @Autowired FarmaceuticoRepository farmaceuticoRepository;
  
  public List<PharmacistResponseDTO> getAll() {
    List<Farmaceutico> farmaceuticos = farmaceuticoRepository.findAll();
    return farmaceuticos.stream().map(f -> new PharmacistResponseDTO(
      f.getId(),
      f.getUsuario().getNombre(),
      f.getFarmacia(),
      f.getTelefono()
    )).collect(Collectors.toList());
  }
}
