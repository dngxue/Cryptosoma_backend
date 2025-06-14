package com.escom.backend.presentation.cripto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;

import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECDH25519 {
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static KeyPair generateEphimeralKeyPair() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("X25519", "BC");
      return keyPairGenerator.generateKeyPair();
    } catch (Exception e) {
      throw new RuntimeException("Error generating ephemeral key pair", e);
    }
  }

  public static byte[] deriveSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
    KeyAgreement ka = KeyAgreement.getInstance("X25519", "BC");
    ka.init(privateKey);
    ka.doPhase(publicKey, true);
    return ka.generateSecret();
  }

  public static PublicKey decodePublicKey(String base64pubKey) throws Exception {
    byte[] encoded = Base64.getDecoder().decode(base64pubKey);
    KeyFactory kf = KeyFactory.getInstance("X25519", "BC");
    return kf.generatePublic(new X509EncodedKeySpec(encoded));
  }

  public static String encodePublicKey(PublicKey publicKey) {
    return Base64.getEncoder().encodeToString(publicKey.getEncoded());
  }
}
