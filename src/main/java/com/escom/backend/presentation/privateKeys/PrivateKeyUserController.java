package com.escom.backend.presentation.privateKeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.security.CreatePrivateKeyUserDTO;
import com.escom.backend.presentation.services.security.PrivateKeyUserService;

@RestController
@RequestMapping("/private")
public class PrivateKeyUserController {
  @Autowired private PrivateKeyUserService privateKeyUserService;

  @PostMapping("/save")
  public ResponseEntity<?> createPrivateKey(@RequestBody CreatePrivateKeyUserDTO dto) {
    return ResponseEntity.ok(privateKeyUserService.createPrivateKeyUser(dto));
  }
}
