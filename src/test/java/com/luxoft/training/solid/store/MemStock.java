package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class MemStock implements Stock {

    private final Map<String, ProductData> products;

    public MemStock() {
        this.products = new HashMap<>();
    }

    public void addProduct(String name, double price, int count) {
        ProductData p;
        try {
            p = findProduct(name);
            p = new ProductData(name, price, p.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new ProductData(name, price, count);
        }
        products.put(name, p);
    }

    public ProductData takeProduct(String name, int count) {
        ProductData productInStock = findProduct(name);
        removeProduct(productInStock, count);
        return new ProductData(productInStock.getName(), productInStock.getPrice(), count);
    }

    private ProductData findProduct(String name) {
        ProductData productInStock = products.get(name);
        if (productInStock == null) {
            throw new ProductNotFoundException(name);
        }
        return productInStock;
    }

    private void removeProduct(ProductData p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new ProductData(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        products.put(p.getName(), p);
    }
}
