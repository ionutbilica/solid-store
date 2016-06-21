package com.luxoft.training.solid.store.receipt;

public class TextProductReceiptLine {

    private final String name;
    private final int count;
    private final double price;
    private final double priceForAll;

    public TextProductReceiptLine(String name, int count, double price, double priceForAll) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.priceForAll = priceForAll;
    }

    @Override
    public String toString() {
        return name + ": " + count + " x " + price + " = " + priceForAll;
    }
}
