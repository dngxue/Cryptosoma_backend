package com.escom.backend.presentation.services.security;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.escom.backend.presentation.cripto.ECDH25519;
import com.escom.backend.presentation.cripto.ECDH25519.KeyPairEncoded;

@Service
public class KeyAgreementService {
  public record KeyAgreementResult(byte[] sharedKey, KeyPairEncoded serverKeyPair) {}

  public KeyAgreementResult generateSharedKey(String publicKeyPaciente) {
    try {
      KeyPairEncoded kpTemp = ECDH25519.generateDHKeyPair();
      System.out.println("Public Key Servidor: " + kpTemp.getPublicKeyBase64());
      byte[] publicKeyPacienteBytes = Base64.getDecoder().decode(publicKeyPaciente);
      byte[] sharedKey = ECDH25519.deriveSharedSecret(kpTemp.privateKeyB64, publicKeyPacienteBytes);
      return new KeyAgreementResult(sharedKey, kpTemp);
    } catch (Exception e) {
      throw new RuntimeException("Ocurrió un error al generar la llave compartida");
    }
  }
}
