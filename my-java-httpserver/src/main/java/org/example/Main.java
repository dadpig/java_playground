package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        HttpServer server = new HttpServer(8080);
        server.start();
    }
}