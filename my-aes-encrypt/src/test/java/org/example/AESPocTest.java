package org.example;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

public class AESPocTest {
    //AES - Advanced Encryption Standard
    //CBC - Cipher Block Chaining
    //PKCS5Padding - padding for block size of 8bytes (ciphertext will be padded to a multiple of 8 bytes)
    //The block size is a property of the used cipher algorithm. For AES it is always 16 bytes.
    //PKCS5Padding is interpreted as a synonym for PKCS7Padding in the cipher specification.
    // It is simply a historical artifact, and rather than change it Sun decided to simply pretend the PKCS5Padding means the same as PKCS7Padding
    // when applied to block ciphers with a blocksize greater than 8 bytes.
    String algorithm = "AES/CBC/PKCS5Padding";

    @Test
    public void given128bitKey_AndStringMessage_thenSuccess() throws Exception {
        String message = "LendingClub - Helping Americans meet their life goals";
        SecretKey key = AESPoc.generateKey(128);
        IvParameterSpec ivParameterSpec = AESPoc.generateInitialVector();

        String cipherText = AESPoc.encrypt(algorithm, message, key, ivParameterSpec);
        String decipherText = AESPoc.decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(message);
        System.out.println(cipherText);

        Assert.assertEquals(message, decipherText);
    }
    @Test
    public void given192bitKey_AndStringMessage_thenSuccess() throws Exception {
        String message = "LendingClub - Helping Americans meet their life goals";
        SecretKey key = AESPoc.generateKey(192);
        IvParameterSpec ivParameterSpec = AESPoc.generateInitialVector();

        String cipherText = AESPoc.encrypt(algorithm, message, key, ivParameterSpec);
        String decipherText = AESPoc.decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(message);
        System.out.println(cipherText);

        Assert.assertEquals(message, decipherText);
    }

    @Test
    public void given256bitKey_AndStringMessage_thenSuccess() throws Exception {
        String message = "LendingClub - Helping Americans meet their life goals";
        SecretKey key = AESPoc.generateKey(256);
        IvParameterSpec ivParameterSpec = AESPoc.generateInitialVector();

        String cipherText = AESPoc.encrypt(algorithm, message, key, ivParameterSpec);
        String decipherText = AESPoc.decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(message);
        System.out.println(cipherText);

        Assert.assertEquals(message, decipherText);
    }


    @Test(expected = InvalidParameterException.class)
    public void givenInvalidKeySize_thenException() throws NoSuchAlgorithmException {
        SecretKey key = AESPoc.generateKey(200);
    }


    @Test(expected = Exception.class)
    public void givenInvalidAlgorithm_thenException() throws Exception {
        String message = "LendingClub - Helping Americans meet their life goals";
        SecretKey key = AESPoc.generateKey(128);
        IvParameterSpec ivParameterSpec = AESPoc.generateInitialVector();

        String cipherText = AESPoc.encrypt("InvalidAlgorithm", message, key, ivParameterSpec);
    }

    @Test(expected = Exception.class)
    public void givenInvalidCipherText_thenException() throws Exception {
        String message = "LendingClub - Helping Americans meet their life goals";
        SecretKey key = AESPoc.generateKey(128);
        IvParameterSpec ivParameterSpec = AESPoc.generateInitialVector();

        String decipherText = AESPoc.decrypt("AES/CBC/PKCS5Padding", "InvalidCipherText", key, ivParameterSpec);
    }
}
