package com.luxoft.training.solid.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {

    private final int id;
    private List<Product> products;
    private static int receiptNo;

    public Cart(int id) {
        this.id = id;
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getFullPriceForAll).sum();
    }

    private double getTotalPriceWithoutVat() {
        return products.stream().mapToDouble(Product::getFullPriceForAll).sum();
    }

    public String getReceipt() {
        StringBuilder s = new StringBuilder("Our Store");
        s.append("Receipt no.: " + ++receiptNo + "\n");
        for (Product p : products) {
            s.append(p.getName() + " " + p.getCount() + " x " + p.getFullPrice() + " = " + p.getFullPriceForAll() + "\n");
        }
        s.append("Total: " + getTotalPrice() + "\n");
        s.append("Date: " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()) + "\n");
        return s.toString();
    }
}
