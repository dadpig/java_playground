package com.example;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class MyObjectConverter{

    private static Unsafe unsafe;
    private final static Map CACHE = new HashMap();
    private final static Map<String, Object> CACHE_v2 = new HashMap<String, Object>();

    private final static Map<String, Object> CACHE_v3 = new HashMap<String, Object>();

    private final static Map<String, Object> CACHE_v3_offset = new HashMap<String, Object>();
    public static Object convert(Object source, Class target) {

        Object targetInstance = null;
        try {
            targetInstance = target.getDeclaredConstructor().newInstance();
            source = source;
            Field[] fields = source.getClass().getDeclaredFields();
            for( Field field : fields ){
                field.setAccessible(true);
                Field targetField = targetInstance.getClass().getDeclaredField(field.getName());
                if(null != targetField
                    && field.getType().getClass().equals(targetField.getType().getClass())){
                    targetField.setAccessible(true);
                    targetField.set(targetInstance, field.get(source));
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetInstance;
    }
    public static Object convertWithCache_V1(Object source, Class target) {

        Object targetInstance = null;
        String targetClazzName = target.getName();
        try {

            if(!CACHE.containsKey(targetClazzName)){
                CACHE.put(targetClazzName, target.getDeclaredConstructor());
            }
            targetInstance = ((Constructor) CACHE.get(targetClazzName)).newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            //cachear os atributos
            String sourceClazzName = source.getClass().getName();
            for( Field sourceField : sourceFields ){
                String sourceFieldzName = sourceClazzName+":"+sourceField.getName();
                if(!CACHE.containsKey(sourceFieldzName)){
                    sourceField.setAccessible(true);
                    CACHE.put(sourceFieldzName, sourceField);
                }

                Field targetField = targetInstance.getClass().getDeclaredField(sourceField.getName());
                if(null != targetField
                    && sourceField.getType().getClass().equals(targetField.getType().getClass())) {
                    targetField.setAccessible(true);
                    targetField.set(targetInstance, ((Field)CACHE.get(sourceFieldzName)).get(source));

                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetInstance;
    }

    public static Object convertWithCacheV2(Object source, Class target) {
        String targetClazzName = target.getName();
        String sourceClazzName = source.getClass().getName();
        Object targetInstance = null;
        try{
            populateCache_v2(target, targetClazzName);
            targetInstance = ((Constructor) CACHE_v2.get(targetClazzName)).newInstance();
            populateCache_v2(source.getClass(), sourceClazzName);

            for(String key :CACHE_v2.keySet()){
                if(key.contains((targetClazzName+":"))){
                  Field targetField = (Field)CACHE_v2.get(key);
                  String sourceKey = sourceClazzName+":"+targetField.getName();
                  Field sourceField = (Field)CACHE_v2.get(sourceKey);
                  if(null != targetField && null != sourceField
                    && targetField.getType().getClass().equals(sourceField.getType().getClass())){
                      targetField.set(targetInstance, sourceField.get(source));
                  }
                }
            }

        }catch(Exception e){}
        return targetInstance;
    }

    private static void populateCache_v2(Class clazz, String clazzName) throws NoSuchMethodException {

        if(!CACHE_v2.containsKey(clazzName)){
            CACHE_v2.put(clazzName, clazz.getDeclaredConstructor());
            for(Field targetField : clazz.getDeclaredFields()){
                String targetFieldName = clazzName +":"+targetField.getName();
                if(!CACHE_v2.containsKey(targetFieldName)){
                    targetField.setAccessible(true);
                    CACHE_v2.put(targetFieldName, targetField);
                }
            }
        }
    }

    public static Object convertWithCacheV3(Object source, Class target) {
        String targetClazzName = target.getName();
        String sourceClazzName = source.getClass().getName();
        Object targetInstance = null;
        try{
            populateCacheV3(target, targetClazzName);
            targetInstance = CACHE_v3.get(targetClazzName);
            populateCacheV3(source.getClass(), sourceClazzName);

            for(String key :CACHE_v3.keySet()){
                if(key.contains((targetClazzName+":"))){
                    Field targetField = (Field)CACHE_v3.get(key);
                    String sourceKey = sourceClazzName+":"+targetField.getName();
                    Field sourceField = (Field)CACHE_v3.get(sourceKey);
                    if(null != targetField && null != sourceField
                        && targetField.getType().getClass().equals(sourceField.getType().getClass())){
                        if(!CACHE_v3_offset.containsKey(key)){
                            CACHE_v3_offset.put(key, getUnsafe().objectFieldOffset(targetField));
                        }
                        if(!CACHE_v3_offset.containsKey(sourceKey)){
                            CACHE_v3_offset.put(sourceKey, getUnsafe().objectFieldOffset(sourceField));
                        }
                        Long targetOffset = (Long) CACHE_v3_offset.get(key);
                        Long sourceOffset = (Long) CACHE_v3_offset.get(sourceKey);
                        if(!CACHE_v3_offset.containsKey(sourceOffset.toString())){
                            CACHE_v3_offset.put(sourceOffset.toString(), getUnsafe().getObject(source, sourceOffset));
                        }
                        Object value = CACHE_v3_offset.get(sourceOffset.toString());
                        getUnsafe().putObject(targetInstance, targetOffset, value);
                    }
                }
            }
        }catch(Exception e){}
        return targetInstance;
    }

    private static void populateCacheV3(Class clazz, String clazzName) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InstantiationException {

        if(!CACHE_v3.containsKey(clazzName)){
            CACHE_v3.put(clazzName, getUnsafe().allocateInstance(clazz));
            for(Field targetField : clazz.getDeclaredFields()){
                String targetFieldName = clazzName +":"+targetField.getName();
                if(!CACHE_v3.containsKey(targetFieldName)){

                    targetField.setAccessible(true);
                    CACHE_v3.put(targetFieldName, targetField);
                }
            }
        }
    }

    public static Unsafe getUnsafe(){
        try {
            if(null == unsafe){
                Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe = (Unsafe) field.get(null);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return unsafe;
    }
}
