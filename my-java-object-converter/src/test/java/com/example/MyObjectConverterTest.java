package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyObjectConverterTest {

    final int NUMBER_OF_PLANTS= 500_000;

    @Test
    void testConvertAnimalIntoPlant() {
        Animal source = new Animal();
        source.setName("Totó");
        source.setAge(15);
        source.setGender('M');

        try{

            Object result = MyObjectConverter.convert(source, Plant.class);
            Assertions.assertTrue(source instanceof Animal );
            Assertions.assertTrue(result instanceof Plant );
            Plant target = (Plant) result;
            Assertions.assertEquals(source.getName(), target.getName() );
            Assertions.assertEquals(source.getAge(), target.getAge() );

        }catch(Exception e){}
    }

    @Test
    void testConvertPlantIntoAnimal() {
        Plant source = new Plant();
        source.setName("Filomena");
        source.setAge(15);
        source.setSize(5.0f);
        try{

            Object result = MyObjectConverter.convert(source, Animal.class);
            Assertions.assertTrue(source instanceof Plant );
            Assertions.assertTrue(result instanceof Animal );
            Animal target = (Animal) result;
            Assertions.assertEquals(target.getName(), source.getName() );
            Assertions.assertEquals(target.getAge(), source.getAge() );
        }catch(Exception e){}

    }
    @Test
    void testConvertAnimalInto500kPlantsWithoutCache() {
        Animal source = new Animal();
        source.setName("Totó");
        source.setAge(15);
        source.setGender('M');


        try{
            long timeStart = System.currentTimeMillis();
            for(int i=0; i<NUMBER_OF_PLANTS; i++){
                Object result = MyObjectConverter.convert(source, Plant.class);
                Assertions.assertTrue(source instanceof Animal );
                Assertions.assertTrue(result instanceof Plant );
                Plant target = (Plant) result;
                Assertions.assertEquals(source.getName(), target.getName() );
                Assertions.assertEquals(source.getAge(), target.getAge() );
            }
            System.out.println("(testConvertAnimalInto500kPlantsWithoutCache)Time taken: "+ (System.currentTimeMillis() - timeStart) + " ms.");

        }catch(Exception e){}
    }
    @Test
    void testConvertAnimalInto500kPlantsWithCacheV1() {
        Animal source = new Animal();
        source.setName("Totó");
        source.setAge(15);
        source.setGender('M');

        try{

            long timeStart = System.currentTimeMillis();
            for(int i=0; i<NUMBER_OF_PLANTS; i++){
                Object result = MyObjectConverter.convertWithCache_V1(source, Plant.class);
                Assertions.assertTrue(source instanceof Animal );
                Assertions.assertTrue(result instanceof Plant );
                Plant target = (Plant) result;
                Assertions.assertEquals(source.getName(), target.getName() );
                Assertions.assertEquals(source.getAge(), target.getAge() );
            }
            System.out.println("(testConvertAnimalInto500kPlantsWithCacheV1)Time taken: "+ (System.currentTimeMillis() - timeStart) + " ms.");

        }catch(Exception e){}
    }

    @Test
    void testConvertAnimalInto500kPlantsWithCacheV2() {
        Animal source = new Animal();
        source.setName("Totó");
        source.setAge(15);
        source.setGender('M');

        try{

            long timeStart = System.currentTimeMillis();
            for(int i=0; i<NUMBER_OF_PLANTS; i++){
                Object result = MyObjectConverter.convertWithCacheV2(source, Plant.class);
                Assertions.assertTrue(source instanceof Animal );
                Assertions.assertTrue(result instanceof Plant );
                Plant target = (Plant) result;
                Assertions.assertEquals(source.getName(), target.getName() );
                Assertions.assertEquals(source.getAge(), target.getAge() );
            }
            System.out.println("(testConvertAnimalInto500kPlantsWithCacheV2)Time taken: "+ (System.currentTimeMillis() - timeStart) + " ms.");

        }catch(Exception e){}
    }


    @Test
    void testConvertAnimalInto500kPlantsWithCacheV3() {
        Animal source = new Animal();
        source.setName("Totó");
        source.setAge(15);
        source.setGender('M');

        try{

            long timeStart = System.currentTimeMillis();
            for(int i=0; i<NUMBER_OF_PLANTS; i++){
                Object result = MyObjectConverter.convertWithCacheV3(source, Plant.class);
                Assertions.assertTrue(source instanceof Animal );
                Assertions.assertTrue(result instanceof Plant );
                Plant target = (Plant) result;
                Assertions.assertEquals(source.getName(), target.getName() );
                Assertions.assertEquals(source.getAge(), target.getAge() );
            }
            System.out.println("(testConvertAnimalInto500kPlantsWithCacheV3)Time taken: "+ (System.currentTimeMillis() - timeStart) + " ms.");

        }catch(Exception e){}
    }
}
