package com.escom.backend.presentation.cripto;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES_GCM {
  static {
    Security.addProvider(new BouncyCastleProvider()); 
  }

  public static String encryptGCM(byte[] aesKeyBytes, byte[] fileContent, String filename) {
    try {
      SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
      byte[] iv = new byte[12];
      SecureRandom random = new SecureRandom();
      random.nextBytes(iv);

      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
      GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
      cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);

      byte[] cipherContent = cipher.doFinal(fileContent);
      
      byte[] output = new byte[iv.length + cipherContent.length];
      
      // src, indice inicial, dest, indice inicial dest, length
      System.arraycopy(iv, 0, output, 0, iv.length);
      System.arraycopy(cipherContent, 0, output, iv.length, cipherContent.length);

      return Base64.getEncoder().encodeToString(output);
      
    } catch (Exception e) {
      throw new RuntimeException("Error during cipher GCM", e);
    }
  }
  
  public static byte[] decryptGCM(byte[] encryptedFileBytes, byte[] aesKeyGCM) throws IOException {
    try {
      byte[] encryptedB64 = Base64.getDecoder().decode(new String(encryptedFileBytes).replaceAll("[\\n\\r]", "").trim());
      
      byte[] iv = new byte[12];
      byte[] cipherText = new byte[encryptedB64.length - 12];
      
      // Separamos el vector IV y los datos cifrados.
      System.arraycopy(encryptedB64, 0, iv, 0, 12);
      System.arraycopy(encryptedB64, 12, cipherText, 0, cipherText.length);
      
      // Generamos la llave y descifrar
      SecretKey aesKey = new SecretKeySpec(aesKeyGCM, "AES");
      
      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
      GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
      
      cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);

      byte[] plainText = cipher.doFinal(cipherText);

      return plainText;
    } catch (Exception e) {
      throw  new IOException("Error al leer el archivo cifrado", e);
    }
  }
}

