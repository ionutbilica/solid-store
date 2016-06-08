package com.luxoft.training.solid.store.persistence;

import java.util.List;

public class CartData {

    private int id;
    private List<ProductData> productsData;
    private boolean hasDelivery;

    public CartData(int id, List<ProductData> productsData, boolean hasDelivery) {
        this.id = id;
        this.productsData = productsData;
        this.hasDelivery = hasDelivery;
    }

    public int getId() {
        return id;
    }

    public List<ProductData> getProductsData() {
        return productsData;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }
}
