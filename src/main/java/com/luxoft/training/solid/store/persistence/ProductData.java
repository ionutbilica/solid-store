package com.luxoft.training.solid.store.persistence;

public class ProductData {
    
    private final String name;
    private final double price;
    private final int count;

    public ProductData(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
