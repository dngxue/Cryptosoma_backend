package com.escom.backend.presentation.cripto;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES_GCM {
  static {
    Security.addProvider(new BouncyCastleProvider()); 
  }

  public static SecretKey generateAESKey(int keySize) throws Exception {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(keySize);
    return keyGen.generateKey();
  }

  public static String encryptGCM(byte[] aesKeyBytes, byte[] fileContent) {
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
  
   /**
   * Función para descifrar un archivo.
   * @param byte[] encyptedBytesB64: Recibe el texto cifrado en bytes.
   * @param byte[] aesKeyGCM: Llave de aes en formato de bytes
   */
  public static byte[] decryptGCM(byte[] encryptedBytes, byte[] aesKeyGCM) throws IOException {
    try {      
      byte[] iv = new byte[12];
      byte[] cipherText = new byte[encryptedBytes.length - 12];
      
      // Separamos el vector IV y los datos cifrados.
      System.arraycopy(encryptedBytes, 0, iv, 0, 12);
      System.arraycopy(encryptedBytes, 12, cipherText, 0, cipherText.length);
      
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

  public static SecretKey deriveAESKeyFromPassword(char[] password, byte[] salt) throws Exception {
    int iterations = 100_000;
    int keyLength = 256;

    PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    byte[] keyBytes = factory.generateSecret(spec).getEncoded();
    return new SecretKeySpec(keyBytes, "AES");
  }

  public static byte[] generateSalt(int length) {
    byte[] salt = new byte[length];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    return salt;
  }

  public static byte[] decryptWithAESGCM(byte[] ciphertext, byte[] iv, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    GCMParameterSpec spec = new GCMParameterSpec(128, iv); // tag 128 bits
    cipher.init(Cipher.DECRYPT_MODE, key, spec);
    return cipher.doFinal(ciphertext);
  }
}

