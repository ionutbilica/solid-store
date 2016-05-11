package com.luxoft.training.solid.store;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final int id;
    private List<Product> products;

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


}
