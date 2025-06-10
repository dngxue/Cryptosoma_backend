package com.escom.backend.presentation.cripto.ECDH;

import java.security.KeyPairGenerator;

public class ECDH {
  public static void generateKeyPair(String curve) throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");

  }
}

// public class ECDH {
//   public static void generatePublicKey(String curve) throws Exception {
//     Security.addProvider(new BouncyCastleProvider());
//     KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "BC");
//     ECGenParameterSpec ecSpec = new ECGenParameterSpec(curve);
//     kpg.initialize(ecSpec);
    
//     KeyPair keyPair = kpg.generateKeyPair();
    
//     PrivateKey privateKey = keyPair.getPrivate();
//     PublicKey publicKey = keyPair.getPublic();
    
//     String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//     String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

//     saveToFile("private_key_ECDH_" + curve, privateKeyBase64);
//     saveToFile("public_key_ECDH_" + curve, publicKeyBase64);
//   }
  
//   /**
//    * Generates a shared key using ECDH protocol.
//    * @param privateKey The local entity's EC private key
//    * @param publicKey The extern PK.
//    * @return
//    * @throws Exception 
//    */
//   public static byte[] generateSharedKey(PrivateKey privateKey, PublicKey publicKey) throws Exception {
//     Security.addProvider(new BouncyCastleProvider());
//     KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
//     keyAgreement.init(privateKey);
//     keyAgreement.doPhase(publicKey, true);
    
//     byte[] sharedKey = keyAgreement.generateSecret();
//     String sharedKey64 = Base64.getEncoder().encodeToString(sharedKey);
//     System.out.println("Shared Key: " + sharedKey64);
//     return sharedKey;
//   }
// }
