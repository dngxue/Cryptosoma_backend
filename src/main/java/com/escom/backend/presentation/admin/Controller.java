package com.escom.backend.presentation.admin;

import com.escom.backend.domain.dto.FarmaceuticoDTO;
import com.escom.backend.domain.dto.MedicoDTO;
import com.escom.backend.domain.dto.PacienteDTO;
import com.escom.backend.domain.dto.admin.ResponseDTO;
import com.escom.backend.presentation.services.AdminService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Controller {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register-doctor")
    public ResponseEntity<ResponseDTO> registrarMedico(@Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.ok(adminService.registrarMedico(dto));
    }

    @PostMapping("/register-pharmacist")
    public ResponseEntity<ResponseDTO> registrarFarmaceutico(@Valid @RequestBody FarmaceuticoDTO dto) {
        return ResponseEntity.ok(adminService.registrarFarmaceutico(dto));
    }

    @PostMapping("/register-patient")
    public ResponseEntity<ResponseDTO> registrarPaciente(@Valid @RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(adminService.registrarPaciente(dto));
    }
}
