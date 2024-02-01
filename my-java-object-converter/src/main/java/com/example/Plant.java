package com.example;

import java.io.Serializable;

public class Plant implements Serializable {
    private String name;
    private Integer age;
    private Float size;


    public String getName(){
        return this.name;
    }
    public Integer getAge(){
        return this.age;
    }
    public Float getSize(){
        return this.size;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(Integer age){
        this.age =  age;
    }
    public void setSize(Float size){
        this.size = size;
    }

    public String toString(){
        return "[name="+name+", age="+age+", "+"size="+size+"]";
    }
}
