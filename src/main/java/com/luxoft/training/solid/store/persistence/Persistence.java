package com.luxoft.training.solid.store.persistence;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;

public interface Persistence {

    void putProduct(String name, Product product) throws PersistenceException;

    Product getProduct(String name) throws PersistenceException;

    void putCart(int cartId, Cart cart) throws PersistenceException;

    Cart getCart(int cartId) throws PersistenceException;
    
    class PersistenceException extends RuntimeException {
        public PersistenceException(Throwable cause) {
            super(cause);
        }
    }
}
