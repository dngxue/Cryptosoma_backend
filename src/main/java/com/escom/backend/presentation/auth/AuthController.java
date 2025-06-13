package com.escom.backend.presentation.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.LoginRequest;
import com.escom.backend.domain.dto.LoginResponse;
import com.escom.backend.domain.dto.security.PublicKeyDTO;
import com.escom.backend.presentation.services.AuthService;
import com.escom.backend.presentation.services.PublicKeyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @Autowired
  private PublicKeyService publicKeyService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/savePublicKey")
  public ResponseEntity<String> savePublicKey(@Valid @RequestBody PublicKeyDTO publicKeyDto) {
    System.out.println("Tipo de clave: " + publicKeyDto.keyType);
    publicKeyService.savePublicKey(publicKeyDto);
    return ResponseEntity.ok("Clave pública almacenada con éxito");
  }
}
