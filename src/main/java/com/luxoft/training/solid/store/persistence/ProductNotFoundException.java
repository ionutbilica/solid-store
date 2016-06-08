package com.luxoft.training.solid.store.persistence;

public class ProductNotFoundException extends PersistenceException {
    public ProductNotFoundException(String productId) {
        super("Product with id [" + productId + "] does not exist.");
    }
}
