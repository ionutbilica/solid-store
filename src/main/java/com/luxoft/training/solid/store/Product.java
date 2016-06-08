package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.persistence.ProductData;

import java.util.Objects;

public final class Product {

    private final String name;
    private final double price;
    private final int count;

    public Product (String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }
    
    public Product (ProductData data) {
        this(data.getName(), data.getPrice(), data.getCount());
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
    
    public ProductData getData() {
        return new ProductData(name, price, count);        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(price, product.price) &&
                Objects.equals(count, product.count) &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, count);
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
