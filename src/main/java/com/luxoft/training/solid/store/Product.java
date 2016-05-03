package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.NotEnoughInStockException;

public class Product {

    public static final double VAT = 0.25;
    private final String id;
    private final double price;
    private int count;

    public Product (String id, double price, int count) {
        this.id = id;
        this.price = price;
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public double getFullPrice() {
        return price + price * VAT;
    }

    public double getFullPriceForAll() {
        return getFullPrice() * count;
    }

    public int getCount() {
        return count;
    }

    public Product getSome(int count) {
        changeCount(-count);
        return new Product(id, price, count);
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
                "id='" + id + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
