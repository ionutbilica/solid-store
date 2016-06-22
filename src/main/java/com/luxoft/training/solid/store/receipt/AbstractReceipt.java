package com.luxoft.training.solid.store.receipt;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReceipt implements Receipt {

    protected static int receiptNo;

    protected List<ProductReceiptLine> productLines;

    protected double deliveryCost;

    protected boolean hasDelivery;
    protected double totalPrice;

    public AbstractReceipt() {
        productLines = new ArrayList<>();
        hasDelivery = false;
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

}
