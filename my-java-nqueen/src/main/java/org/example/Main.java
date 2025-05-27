package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        Nqueen nqueen = new Nqueen(8);
        nqueen.solve(0);
        System.out.println("size 0!");
        nqueen.solve(4);
        System.out.println("size 4");
        nqueen.solve(8);
        System.out.println("size 8");
        nqueen.solve(16);
        System.out.println("size 16");

    }
}