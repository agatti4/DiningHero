package com.example.finalproject;

public class ListElement {
    private String name;
    private double price;

    /**
     * constructor that assigns name parameter to the name of a new element
     * @param name name of new element
     */
    public ListElement(String name, double price){
        this.name = name;
        this.price = price;
    }

    /**
     *  returns name
     * @return name
     */
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
