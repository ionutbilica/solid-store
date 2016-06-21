package com.luxoft.training.solid.store.receipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.luxoft.training.solid.store.Cart.DELIVERY_COST;

public class TextReceipt implements Receipt {

    private static int receiptNo;

    private List<TextProductReceiptLine> productLines;

    private double deliveryCost;

    private boolean hasDelivery;
    private double totalPrice;

    public TextReceipt() {
        productLines = new ArrayList<>();
        hasDelivery = false;
    }

    @Override
    public void addProduct(String name, int count, double price, double priceForAll) {
        productLines.add(new TextProductReceiptLine(name, count, price, priceForAll));
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public void addDelivery(double cost) {
        hasDelivery = true;
        deliveryCost = cost;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + (++receiptNo) + "\n");
        for (TextProductReceiptLine p : productLines) {
            s.append(p.toString() + "\n");
        }
        if (hasDelivery) {
            s.append("Delivery: " + DELIVERY_COST + "\n");
        }
        s.append("Total: " + totalPrice + "\n");
        s.append("Date: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "\n");
        return s.toString();
    }
}
