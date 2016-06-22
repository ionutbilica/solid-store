package com.luxoft.training.solid.store.receipt;

public abstract class ProductReceiptLine {

    protected final String name;
    protected final int count;
    protected final double price;
    protected final double priceForAll;

    public ProductReceiptLine(String name, int count, double price, double priceForAll) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.priceForAll = priceForAll;
    }
}
