package org.example.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NotEmptylValidation implements Validation{

    public String validate(Field field, Object obj){
        try {
            field.setAccessible(true);
            if (null == field.get(obj) || "".equals(field.get(obj).toString().trim())){
                return field.getName()+" Not be Empty";
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
