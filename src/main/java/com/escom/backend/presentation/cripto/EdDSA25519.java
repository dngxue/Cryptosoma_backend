package com.escom.backend.presentation.cripto;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EdDSA25519 {
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  // public static boolean verifySignature(byte[] publicKeyBytes, byte[] message, byte[] signature) throws Exception {
  //   KeyFactory keyFactory = KeyFactory.getInstance("Ed25519", "BC");
  //   PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

  //   Signature verifier = Signature.getInstance("Ed25519", "BC");
  //   verifier.initVerify(publicKey);
  //   verifier.update(message);
  //   return verifier.verify(signature);
  // }

  public static boolean verifySignature(byte[] publicKeyBytes, byte[] message, byte[] signature) {
    Ed25519PublicKeyParameters pubKeyParams = new Ed25519PublicKeyParameters(publicKeyBytes, 0);
    Ed25519Signer verifier = new Ed25519Signer();
    verifier.init(false, pubKeyParams);
    verifier.update(message, 0, message.length);
    return verifier.verifySignature(signature);
  }
}