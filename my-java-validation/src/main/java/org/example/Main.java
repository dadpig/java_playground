package org.example;

import org.example.model.Person;
import org.example.validation.MyBeanValidation;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        Person p1 = new Person(8, "Mariana");
        Person p2 = new Person(null, "Mariana");
        Person p3 = new Person(4, " ");
        MyBeanValidation validation = new MyBeanValidation();
        validation.validate(p1);
        validation.validate(p2);
        validation.validate(p3);
    }
}