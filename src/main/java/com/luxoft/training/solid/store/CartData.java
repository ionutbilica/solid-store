package com.luxoft.training.solid.store;

import java.util.Collections;
import java.util.List;

public class CartData {

    private final int id;
    private final List<ProductData> products;
    private final boolean hasDelivery;

    public CartData(int id) {
        this(id, Collections.<ProductData>emptyList(), false);
        
    }

    public CartData(int id, List<ProductData> products, boolean hasDelivery) {
        this.id = id;
        this.products = products;
        this.hasDelivery = hasDelivery;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }

    public int getId() {
        return id;
    }
}
