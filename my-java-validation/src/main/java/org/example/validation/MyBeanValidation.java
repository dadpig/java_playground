package org.example.validation;

import org.example.annotations.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBeanValidation {


    private static Map<String, Field> CACHE = new HashMap<>();
    private static Map<String, Validation> factory = new HashMap<>();



    static {
        factory.put("NotEmpty",new NotEmptylValidation() );
        factory.put("NotNull",new NotNullValidation() );
    }


    public static void main(String[] args) {
         validate(null);
    }
    @NotNull
    public static List<String> validate(@NotNull Object obj){
        List<String> constraints = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        //1. nao esta cacheando o Objeto separar atributos dos seus respectivos objetos
        for(Field field: fields){
            if(!CACHE.containsKey(field.getName())){
                CACHE.put(field.getName(), field);
            }
            Field cachedField =  CACHE.get(field.getName());
            String message = null;

            message = buildValidation(cachedField.getDeclaredAnnotations()[0]).validate(cachedField, obj);
            if(null != message)
                constraints.add(message);
        }
        return constraints;
    }

    private static Validation buildValidation(Annotation annotation){
//troca por factory usando static Map lookup
        //v2 scanear package para incluir no classloader no mapa automagicamente
         return factory.get(annotation.annotationType().getSimpleName());
    }
}
