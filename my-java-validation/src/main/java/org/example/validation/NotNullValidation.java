package org.example.validation;

import java.lang.reflect.Field;

public class NotNullValidation implements Validation{

     public String validate(Field field, Object obj){
        try{
            field.setAccessible(true);
            if (null == field.get(obj)){
                return field.getName()+" Not be Null";
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
