package com.example.finalproject;

public class CartListElement {
    private String name;
    private double price;
    private int quantity;
    private int quantityIndex;

    public CartListElement(String name, double price, int quantity, int quantityIndex){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.quantityIndex =  quantityIndex;
    }

    public String getName() { return name; }

    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public int getQuantityIndex() {
        return quantityIndex;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
