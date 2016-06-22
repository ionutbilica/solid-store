package com.luxoft.training.solid.store.receipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.luxoft.training.solid.store.Cart.DELIVERY_COST;

public class TextReceipt extends AbstractReceipt {

    @Override
    public void addProduct(String name, int count, double price, double priceForAll) {
        productLines.add(new TextProductReceiptLine(name, count, price, priceForAll));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + (++receiptNo) + "\n");
        for (ProductReceiptLine p : productLines) {
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
