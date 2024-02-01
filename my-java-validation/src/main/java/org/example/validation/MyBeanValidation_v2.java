package org.example.validation;

import org.example.annotations.NotEmpty;
import org.example.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class MyBeanValidation_v2 {

    private final static Map<Class<?>, Validation> factory = new HashMap<>();

    private final static Map<String, List<Field>>_CACHE = new HashMap<>();

    static {

        factory.put(NotEmpty.class,new NotEmptylValidation() );
        factory.put(NotNull.class,new NotNullValidation() );
    }
    public static List<String> validate(Object obj){
        List<String> constraints = new ArrayList<>();
        String _clazz = obj.getClass().getSimpleName();
        if(!_CACHE.containsKey(_clazz)){
            List<Field> cached = new ArrayList<>();
            Field[] fields = obj.getClass().getDeclaredFields();
            Collections.addAll(cached, fields);
            _CACHE.put(_clazz, cached);
        }
        List<Field> cachedFields = _CACHE.get(_clazz);
        for(Field field: cachedFields){
            String message = buildValidation(field.getDeclaredAnnotations()[0]).validate(field, obj);
            if(null != message)
                constraints.add(message);
        }
        return constraints;
    }

    private static Validation buildValidation(Annotation annotation){
         return factory.get(annotation.annotationType());
    }
}
