package com.luxoft.training.solid.store;

public final class Product {

    private final String name;
    private final double price;
    private final int count;

    public Product (String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Product(ProductData productData) {
        this(productData.getName(), productData.getPrice(), productData.getCount());
    }

    public double getPrice() {
        return price;
    }

    public double getPriceForAll() {
        return getPrice() * count;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    public void fillInReceipt(Receipt receipt) {
        receipt.addProduct(name, count, price, getPriceForAll());
    }

    public ProductData getData() {
        return new ProductData(name, price, count);
    }
}
