package com.code.generator.transform;

import com.code.generator.metadata.StructClass;
import com.code.generator.transform.DynamicGenerator;
import com.code.generator.util.Yaml;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DynamicGeneratorTest {

    Yaml yml = new Yaml();
    @Before
    public void setup(){
        yml.mapToStructClass("descriptor.yml");
    }
    @Test
    public void testDynamicGeneration() {
        StructClass clazz = yml.toStructClass();
        DynamicGenerator dynamicGenerator = new DynamicGenerator(clazz);
        Assert.assertEquals(clazz.getMethods().getFirst().implementationReturn(), dynamicGenerator.generate());
    }

    @Test
    public void testSameStructClass() {
        String expected = """
                //package org.example;


                public class Person{

                    private String name;
                    private Integer size;
                    private Integer age;
                    public String toString (){
                        return "Person.toString() -> It works!";
                    }

                }""";
        StructClass clazz = yml.toStructClass();
        DynamicGenerator dynamicGenerator = new DynamicGenerator(clazz);
        Assert.assertEquals(expected, dynamicGenerator.generate());
    }
}
