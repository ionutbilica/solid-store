package com.luxoft.training.solid.store;

public class Product {

    public static final double VAT = 0.25;
    private final String id;
    private final double price;

    public Product (String id, double price) {
        this.id = id;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getFullPrice() {
        return price + price * VAT;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}
