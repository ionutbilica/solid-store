package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.receipt.Receipt;
import com.luxoft.training.solid.store.receipt.ReceiptFactory;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static final double DELIVERY_COST = 12;
    private final int id;
    private final ReceiptFactory receiptFactory;
    private List<Product> products;
    private boolean hasDelivery;

    public Cart(int id, ReceiptFactory receiptFactory) {
        this.id = id;
        this.receiptFactory = receiptFactory;
        hasDelivery = false;
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalPrice() {
        double productsTotal = 0;
        for (Product p : products) {
            productsTotal += p.getPriceForAll();
        }
        double deliveryCost = hasDelivery ? DELIVERY_COST : 0;
        return productsTotal + deliveryCost;
    }

    public String fillInReceipt(Receipt receipt) {
        for (Product p : products) {
            p.fillInReceipt(receipt);
        }
        if (hasDelivery) {
            receipt.addDelivery(DELIVERY_COST);
        }
        receipt.setTotalPrice(getTotalPrice());
        return receipt.toString();
    }

    public void addDelivery() {
        this.hasDelivery = true;
    }
}
