package com.escom.backend.presentation.cripto;

import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECDH25519 {
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static class KeyPairEncoded {
    public final byte[] privateKeyB64;
    public final byte[] publicKeyB64;

    public KeyPairEncoded(byte[] priv, byte[] pub) {
      this.privateKeyB64 = priv;
      this.publicKeyB64 = pub;
    }

    public String getPrivateKeyBase64() {
      return Base64.getEncoder().encodeToString(privateKeyB64);
    }

    public String getPublicKeyBase64() {
      return Base64.getEncoder().encodeToString(publicKeyB64);
    }
  }

  public static KeyPairEncoded generateDHKeyPair() throws Exception {
    X25519KeyPairGenerator generator = new X25519KeyPairGenerator();
    generator.init(new X25519KeyGenerationParameters(new SecureRandom()));
    AsymmetricCipherKeyPair keyPair = generator.generateKeyPair();
    X25519PrivateKeyParameters privateKey = (X25519PrivateKeyParameters) keyPair.getPrivate();
    X25519PublicKeyParameters publicKey = (X25519PublicKeyParameters) keyPair.getPublic();

    return new KeyPairEncoded(privateKey.getEncoded(), publicKey.getEncoded());
  }

  public static byte[] deriveSharedSecret(byte[] privateKeyRaw, byte[] otherPublicKeyRaw) {
    X25519PrivateKeyParameters priv = new X25519PrivateKeyParameters(privateKeyRaw, 0);
    X25519PublicKeyParameters pub = new X25519PublicKeyParameters(otherPublicKeyRaw, 0);

    byte[] sharedSecret = new byte[32];
    priv.generateSecret(pub, sharedSecret, 0);
    return sharedSecret;
  }

  public static byte[] decodePublicKeyRaw(String base64pubKey) {
    return Base64.getDecoder().decode(base64pubKey);
  }

  public static String encodePublicKeyRaw(byte[] pubKeyRaw) {
    return Base64.getEncoder().encodeToString(pubKeyRaw);
  }
}
