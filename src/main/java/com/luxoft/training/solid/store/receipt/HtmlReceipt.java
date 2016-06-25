package com.luxoft.training.solid.store.receipt;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.luxoft.training.solid.store.Cart.DELIVERY_COST;

public class HtmlReceipt extends AbstractReceipt {

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + (++receiptNo) + "\n");

        appendProducts(s);

        if (hasDelivery) {
            s.append("<div><b>Delivery</b>: " + DELIVERY_COST + "</div>\n");
        }
        s.append("<div><b>Total</b>: " + totalPrice + "</div>\n");
        s.append("<div><b>Date</b>: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "</div>\n");
        return s.toString();
    }

    private void appendProducts(StringBuilder s) {
        for (ProductReceiptLine p : productLines) {
            s.append("<div><b>" + p.name + "</b>: " + p.count + " x " + p.price + " = " + p.priceForAll + "</div>" + "\n");
        }
    }
}
