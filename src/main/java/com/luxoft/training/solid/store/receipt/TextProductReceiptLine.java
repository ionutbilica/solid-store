package com.luxoft.training.solid.store.receipt;

public class TextProductReceiptLine extends ProductReceiptLine {

    public TextProductReceiptLine(String name, int count, double price, double priceForAll) {
        super(name, count, price, priceForAll);
    }

    @Override
    public String toString() {
        return name + ": " + count + " x " + price + " = " + priceForAll;
    }
}
