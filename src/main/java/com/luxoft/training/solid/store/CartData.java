package com.luxoft.training.solid.store;

import java.util.List;

public class CartData {

    private int id;
    private List<Product> products;
    private boolean hasDelivery;

    public CartData(int id, List<Product> products, boolean hasDelivery) {
        this.id = id;
        this.products = products;
        this.hasDelivery = hasDelivery;
    }

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }
}
