package com.example;

public class Animal {
    private String name;
    private Integer age;
    private Character gender;

    public String getName(){
        return this.name;
    }
    public Integer getAge(){
        return this.age;
    }
    public Character getGender(){
        return this.gender;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(Integer age){
        this.age =  age;
    }
    public void setGender(Character gender){
        this.gender = gender;
    }
}
