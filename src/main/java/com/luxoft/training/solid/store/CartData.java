package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.Product;

import java.io.Serializable;
import java.util.List;

public class CartData implements Serializable {

    private final int id;
    private final List<Product> products;
    private final boolean hasDelivery;

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
