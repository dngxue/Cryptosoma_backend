package com.escom.backend.presentation.admin;

import com.escom.backend.domain.dto.FarmaceuticoDTO;
import com.escom.backend.domain.dto.MedicoDTO;
import com.escom.backend.domain.dto.PacienteDTO;
import com.escom.backend.presentation.services.AdminService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Controller {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register-doctor")
    public ResponseEntity<String> registrarMedico(@Valid @RequestBody MedicoDTO dto) {
        adminService.registrarMedico(dto);
        return ResponseEntity.ok("Médico registrado exitosamente");
    }

    @PostMapping("/register-pharmacist")
    public ResponseEntity<String> registrarFarmaceutico(@Valid @RequestBody FarmaceuticoDTO dto) {
        adminService.registrarFarmaceutico(dto);
        return ResponseEntity.ok("Farmacéutico registrado exitosamente");
    }

    @PostMapping("/register-patient")
    public ResponseEntity<String> registrarPaciente(@Valid @RequestBody PacienteDTO dto) {
        adminService.registrarPaciente(dto);
        return ResponseEntity.ok("Paciente registrado exitosamente");
    }
}
