package com.example.demo.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtil {

    public static class EncryptedData {
        public byte[] salt;
        public byte[] iv;
        public byte[] cipherText;

        public EncryptedData(byte[] salt, byte[] iv, byte[] cipherText) {
            this.salt = salt;
            this.iv = iv;
            this.cipherText = cipherText;
        }
    }

    private static SecretKey deriveKey(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static EncryptedData encrypt(String plainText, String masterPassword) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        byte[] iv = new byte[16];
        random.nextBytes(salt);
        random.nextBytes(iv);

        SecretKey key = deriveKey(masterPassword.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return new EncryptedData(salt, iv, cipherBytes);
    }

    public static String decrypt(EncryptedData d, String masterPassword) throws Exception {
        SecretKey key = deriveKey(masterPassword.toCharArray(), d.salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(d.iv));
        byte[] plainBytes = cipher.doFinal(d.cipherText);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }
}
