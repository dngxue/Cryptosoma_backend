package com.escom.backend.presentation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.users.get.GetPharmacist;
import com.escom.backend.domain.entities.users.Farmaceutico;
import com.escom.backend.domain.repositories.FarmaceuticoRepository;

@Service
public class PharmacistService {
  @Autowired FarmaceuticoRepository farmaceuticoRepository;
  
  public List<GetPharmacist> getAll() {
    List<Farmaceutico> farmaceuticos = farmaceuticoRepository.findAll();
    return farmaceuticos.stream().map(f -> new GetPharmacist(
      f.getId(),
      f.getUsuario().getNombre(),
      f.getFarmacia(),
      f.getTelefono()
    )).collect(Collectors.toList());
  }
}
