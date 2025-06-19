package com.escom.backend.presentation.farmaceutico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.users.get.PharmacistResponseDTO;
import com.escom.backend.presentation.services.PharmacistService;

@RestController
@RequestMapping("/pharmacist")
public class FarmaceuticoController {
  @Autowired private PharmacistService pharmacistService;

  @GetMapping("/")
  public ResponseEntity<List<PharmacistResponseDTO>> getPharmacists() {
    List<PharmacistResponseDTO> lista = pharmacistService.getAll();
    return ResponseEntity.ok(lista);
  }
}
