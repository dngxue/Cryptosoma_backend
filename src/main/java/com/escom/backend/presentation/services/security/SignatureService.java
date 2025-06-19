package com.escom.backend.presentation.services.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.escom.backend.presentation.cripto.EdDSA25519;

@Service
public class SignatureService {
  public void verifySignature(byte[] message, String signatureB64, String publicKeyB64) {
    byte[] signature = Base64.getDecoder().decode(signatureB64);
    byte[] publicKey = Base64.getDecoder().decode(publicKeyB64);
    boolean isValid = EdDSA25519.verifySignature(publicKey, message, signature);
    if(!isValid) {
      throw new RuntimeException("Firma no válida");
    }
  }
}
