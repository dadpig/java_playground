package com.code.generator.metadata;

import java.util.ArrayList;
import java.util.List;

public class StructClass {
    private String className;
    private String packageName;
    private List<StructField> fields;
    private List<StructMethod> methods;

    private StructClass(){
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    private String fieldToString(){
        String fieldToStr = "";
        for (StructField field: fields) {
            fieldToStr+=field.toString();
        }
        return fieldToStr;
    }

    private String methodToString(){
        String methodToStr = "";
        for (StructMethod method: methods) {
            methodToStr+=method.toString();
        }
        return methodToStr;
    }
    @Override
    public String toString() {

        return "//package " + packageName+";\n\n\n"+
                "public class " + className + "{\n\n" +
                fieldToString() +
                methodToString() +
                "\n}";
    }

    public void addField(StructField field){
        this.fields.add(field);
    }

    public void addMethod(StructMethod method){
        this.methods.add(method);
    }

    public String className() {
        return className;
    }

    public String packageName() {
        return packageName;
    }

    public List<StructMethod> getMethods(){
        return methods;
    }
    public static class Builder{
         private StructClass structClass;

         public Builder(){
             this.structClass = new StructClass();
         }

         public Builder withClassName(String className){
             this.structClass.className=className;
             return this;
         }

         public Builder withPackageName(String packageName){
             this.structClass.packageName=packageName;
             return this;
         }

         public StructClass build() {
             return this.structClass;
         }
     }
}
