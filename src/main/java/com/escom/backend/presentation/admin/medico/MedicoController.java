package com.escom.backend.presentation.admin.medico;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.presentation.services.UsuarioService;


@RestController
@RequestMapping("/user")
public class MedicoController {
  @Autowired
  private UsuarioService usuarioService;
  @GetMapping("/{matricula}")
  public ResponseEntity<?> getPrescriptionUser(@PathVariable("matricula") String matricula) {
    return ResponseEntity.ok(usuarioService.getUsuarioByMatricula(matricula));
  }
}

