package com.luxoft.training.solid.store.persistence;

public class CartNotFoundException extends PersistenceException {

    public CartNotFoundException(int cartId) {
        super("Cart with id [" + cartId + "] does not exist.");
    }
}
