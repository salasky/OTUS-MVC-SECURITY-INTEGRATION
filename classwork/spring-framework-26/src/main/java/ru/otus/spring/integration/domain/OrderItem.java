package ru.otus.spring.integration.domain;

public class OrderItem {

    private String itemName;

    public OrderItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setName(String name){
        this.itemName=name;
    }
}
