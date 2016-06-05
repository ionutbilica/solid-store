package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.NotEnoughInStockException;

public class Product {

    private final String name;
    private final double price;
    private int count;

    public Product (String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public double getFullPriceForAll() {
        return getPrice() * count;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public Product takeSome(int count) {
        changeCount(-count);
        return new Product(name, price, count);
    }

    private void changeCount(int delta) {
        if (count + delta < 0) {
            throw new NotEnoughInStockException(this, delta);
        }
        count += delta;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
