package com.escom.backend.presentation.privateKeys;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escom.backend.domain.dto.privateKeyUser.GetPrivateKeyUserDTO;
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

  @GetMapping("/{userId}")
  public ResponseEntity<GetPrivateKeyUserDTO> getPrivateKeyUser(@PathVariable("userId") UUID userId) {
    return ResponseEntity.ok(privateKeyUserService.getEncryptedPrivateKeyByUserId(userId));
  }
}
