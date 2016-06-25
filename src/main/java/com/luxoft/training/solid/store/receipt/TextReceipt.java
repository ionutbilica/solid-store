package com.luxoft.training.solid.store.receipt;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.luxoft.training.solid.store.Cart.DELIVERY_COST;

public class TextReceipt extends AbstractReceipt {

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + (++receiptNo) + "\n");

        appendProducts(s);

        if (hasDelivery) {
            s.append("Delivery: " + DELIVERY_COST + "\n");
        }
        s.append("Total: " + totalPrice + "\n");
        s.append("Date: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "\n");
        return s.toString();
    }

    private void appendProducts(StringBuilder s) {
        for (ProductReceiptLine p : productLines) {
            s.append(p.name + ": " + p.count + " x " + p.price + " = " + p.priceForAll + "\n");
        }
    }
}
