package org.example.validation;

import java.lang.reflect.Field;

public interface Validation {
    public String validate(Field field, Object obj);
}
