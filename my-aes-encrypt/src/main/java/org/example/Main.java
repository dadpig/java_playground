package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        AES test = new AES("mykeywith128bits");
        String enc_msg = test.encrypt("This is AES Test");
        System.out.println(enc_msg);
        String dec_msg = test.decrypt(enc_msg);
        System.out.println(dec_msg);
    }
}