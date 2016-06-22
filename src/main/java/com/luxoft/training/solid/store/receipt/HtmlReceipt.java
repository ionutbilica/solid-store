package com.luxoft.training.solid.store.receipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.luxoft.training.solid.store.Cart.DELIVERY_COST;

public class HtmlReceipt extends AbstractReceipt {

    @Override
    public void addProduct(String name, int count, double price, double priceForAll) {
        productLines.add(new HtmlProductReceiptLine(name, count, price, priceForAll));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + (++receiptNo) + "\n");
        for (ProductReceiptLine p : productLines) {
            s.append(p.toString() + "\n");
        }
        if (hasDelivery) {
            s.append("<div><b>Delivery</b>: " + DELIVERY_COST + "</div>\n");
        }
        s.append("<div><b>Total</b>: " + totalPrice + "</div>\n");
        s.append("<div><b>Date</b>: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "</div>\n");
        return s.toString();
    }
}
