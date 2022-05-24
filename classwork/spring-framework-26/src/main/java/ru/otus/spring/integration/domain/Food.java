package ru.otus.spring.integration.domain;


public class Food {

    private String name;

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }


}
