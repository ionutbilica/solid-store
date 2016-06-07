package com.luxoft.training.solid.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {

    public static final double DELIVERY_COST = 12;
    private final int id;
    private List<Product> products;
    private static int receiptNo;
    private boolean hasDelivery;

    public Cart(int id) {
        this.id = id;
        hasDelivery = false;
        products = new ArrayList<>();
    }

    public Cart(CartData cartData) {
        id = cartData.getId();
        products = new ArrayList<>(cartData.getProducts());
        hasDelivery = cartData.isHasDelivery();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalPrice() {
        double productsTotal = products.stream().mapToDouble(Product::getFullPriceForAll).sum();
        double deliveryCost = hasDelivery ? DELIVERY_COST : 0;
        return productsTotal + deliveryCost;
    }

    public String getReceipt() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + ++receiptNo + "\n");
        for (Product p : products) {
            s.append(p.getName() + ": " + p.getCount() + " x " + p.getPrice() + " = " + p.getFullPriceForAll() + "\n");
        }
        if (hasDelivery) {
            s.append("Delivery: " + DELIVERY_COST + "\n");
        }
        s.append("Total: " + getTotalPrice() + "\n");
        s.append("Date: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "\n");
        return s.toString();
    }

    public void addDelivery() {
        this.hasDelivery = true;
    }

    public CartData getData() {
        return new CartData(id, new ArrayList<>(products), hasDelivery);
    }
}
