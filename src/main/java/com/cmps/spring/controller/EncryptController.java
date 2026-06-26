package com.cmps.spring.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Controller
public class EncryptController {

    @GetMapping("/encrypt")
    public String Encrypt(Model model){
      try {
          String sampleText = "こんにちは";
          model.addAttribute("sampleText", sampleText);    

          final String secretKey = "iS5KfTPfn4xLjYYy";
          final String initVector = "ncfeKKfPYedi9hJs";
          final String algorithm = "AES/CBC/PKCS5Padding";

          final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
          final IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
          final Cipher cipher = Cipher.getInstance(algorithm);
          cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

          final byte[] source = sampleText.getBytes(StandardCharsets.UTF_8);
          byte[] encryptBytes = cipher.doFinal(source);
          model.addAttribute("encryptBytes", encryptBytes);

          // 復号化
          Cipher decrypter = Cipher.getInstance(algorithm);
          decrypter.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
          String decryptText = new String(decrypter.doFinal(encryptBytes));
          model.addAttribute("decryptText", decryptText);

        } catch (Exception e) {
          e.getStackTrace();
          model.addAttribute("error", "暗号化に失敗しました");
        }

        return "encrypt/encrypt";
   }
    
    @GetMapping("/springEncrypt")
    public String encryptSpring(Model model) {
        String sampleText = "こんにちは";
        model.addAttribute("sampleText", sampleText);
        final byte[] source = sampleText.getBytes(StandardCharsets.UTF_8);
        
        String encryptSecretKey = "GOV2dkHGQcE1ZcX8";
        String salt = KeyGenerators.string().generateKey();
        
        BytesEncryptor binaryEncryptor = Encryptors.stronger(encryptSecretKey, salt);
        
        String gcmEncryptedBinaryData = Base64.getEncoder()
                .encodeToString(binaryEncryptor.encrypt(source));
        model.addAttribute("gcmEncryptedBinaryData", gcmEncryptedBinaryData);
        byte[] gcmDecryptedBinaryData = binaryEncryptor.decrypt(Base64.getDecoder().decode(gcmEncryptedBinaryData));
        String gcmDecryptedText = new String(gcmDecryptedBinaryData, StandardCharsets.UTF_8);
        model.addAttribute("gcmDecryptedText", gcmDecryptedText);
        
        String cbcEncryptedBinaryData = Base64.getEncoder()
                .encodeToString(binaryEncryptor.encrypt(sampleText.getBytes()));
        model.addAttribute("cbcEncryptedBinaryData", cbcEncryptedBinaryData);
        byte[] decryptedBinaryData = binaryEncryptor.decrypt(Base64.getDecoder().decode(cbcEncryptedBinaryData));
        String cbcDecryptedText = new String(decryptedBinaryData, StandardCharsets.UTF_8);
        model.addAttribute("cbcDecryptedText", cbcDecryptedText);
        
        String gcmText = "GCM方式です";
        model.addAttribute("gcmText", gcmText);
        TextEncryptor aesGcmTextEncryptor = Encryptors.delux(encryptSecretKey, salt);
        String encryptedData = aesGcmTextEncryptor.encrypt(gcmText);
        model.addAttribute("encryptedData", encryptedData);
        String gcmDecrypted = aesGcmTextEncryptor.decrypt(encryptedData);
        model.addAttribute("gcmDecrypted", gcmDecrypted);
        
        String cbcText = "CBC方式です";
        model.addAttribute("cbcText", cbcText);
        TextEncryptor cbcTextEncryptor = Encryptors.text(cbcText, salt);
        String encryptedCbcData = cbcTextEncryptor.encrypt(cbcText);
        model.addAttribute("encryptedCbcData", encryptedCbcData);
        String decryptedCbcData = cbcTextEncryptor.decrypt(encryptedCbcData);
        model.addAttribute("decryptedCbcData", decryptedCbcData);

        return "encrypt/encryptSpring";
    }
}