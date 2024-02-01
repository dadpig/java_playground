package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Yaml yml = new Yaml();
        yml.mapToStructClass("descriptor.yml");
        StructClass clazz = yml.toStructClass();
        System.out.println(clazz);
        DynamicGenerator dynamicGenerator = new DynamicGenerator(clazz);
        try {
            dynamicGenerator.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}