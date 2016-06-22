package com.luxoft.training.solid.store.receipt;

public class HtmlProductReceiptLine extends ProductReceiptLine {

    public HtmlProductReceiptLine(String name, int count, double price, double priceForAll) {
        super(name, count, price, priceForAll);
    }

    @Override
    public String toString() {
        return "<div><b>" + name + "</b>: " + count + " x " + price + " = " + priceForAll + "</div>";
    }
}
