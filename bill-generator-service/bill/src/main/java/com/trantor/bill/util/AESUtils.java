package com.trantor.bill.util;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class AESUtils {
    @Setter
    private static String KEY;

    public static String encrypt(String value) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting value", e);
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting value", e);
        }
    }
}

//// In your application.properties file
//
//aes.key=static_key
//
//// In your POST API controller
//
//@PostMapping("/encrypt")
//public Mono<Void> encryptValue(@RequestBody String value) {
//        String encryptedValue = AESUtils.encrypt(value);
//        // Save encryptedValue to database
//        return Mono.empty();
//        }
//
//// In your GET API controller
//
//@GetMapping("/decrypt")
//public Mono<String> decryptValue() {
//        // Read encryptedValue from database
//        String decryptedValue = AESUtils.decrypt(encryptedValue);
//        return Mono.just(decryptedValue);
//        }
//        In this code, the @Value("${aes.key}") annotation is used to read the encryption key from the application.properties file. The aes.key key in the application.properties file has the value static_key, which is used as the encryption key in the AESUtils class.
//





