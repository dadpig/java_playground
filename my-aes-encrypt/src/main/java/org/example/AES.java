package org.example;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {

    /* 3 types of AES Key
    32 character the key will be 256bit
    24 character the key will be 192bit
    16 character the key will be 128bit
     */
    private static String key;

    private static final String ALGORITHM_AES = "AES";
    /*used to cipher and decipher message*/
    private static Cipher cipher;
    /* interface of symmetrical key*/
    private static SecretKey secretKey;
    /* implementation */
    private static SecretKeySpec secretKeySpec;


    public AES(String key) {
        this.key = key;
        try {
            this.cipher = Cipher.getInstance(ALGORITHM_AES);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String decrypt(String message){
        String decryptStr = null;
        try {
            secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptStr = new String(cipher.doFinal(message.getBytes()));

        }catch(Exception e){
            e.printStackTrace();
        }
        return decryptStr;
    }

    public static String encrypt(String message){
        String encryptStr = null;
        try {
            secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptStr = new String(cipher.doFinal(message.getBytes()));

        }catch(Exception e){
            e.printStackTrace();
        }
        return encryptStr;
    }


    public static String encryptPasswordBased(String algorithm, String plainText, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return Base64.getEncoder()
                .encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decryptPasswordBased(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return new String(cipher.doFinal(Base64.getDecoder()
                .decode(cipherText)));
    }
}
