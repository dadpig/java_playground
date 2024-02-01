package org.example.model;

import org.example.annotations.NotEmpty;
import org.example.annotations.NotNull;

public class Person {

    @NotNull
    private Integer age;

    @NotEmpty
    private String name;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
}
