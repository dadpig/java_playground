package org.example;

import org.example.model.Person;
import org.example.validation.MyBeanValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestValidation {

    @Test
    public void testPersonSuccess(){
        Person p1 = new Person(8, "Mariana");
        Assertions.assertEquals(Collections.emptyList(), MyBeanValidation.validate(p1));
    }

    @Test
    public void testPersonNullAge(){
        Person p2 = new Person(null, "Mariana");
        Assertions.assertEquals(Arrays.asList("age Not be Null"), MyBeanValidation.validate(p2));
    }

    @Test
    public void testPersonEmptyName(){
        Person p3 = new Person(4, " ");
        Assertions.assertEquals(Arrays.asList("name Not be Empty"), MyBeanValidation.validate(p3));
    }

    @Test
    public void testPersonNullAgeEmptyName(){
        Person p3 = new Person(null, " ");
        List<String>constraints = MyBeanValidation.validate(p3);
        constraints.stream().forEach(a -> System.out.println(a));
        Assertions.assertEquals(Boolean.TRUE,constraints.containsAll(Arrays.asList("age Not be Null","name Not be Empty")) );
    }
}
