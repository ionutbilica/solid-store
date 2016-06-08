package com.luxoft.training.solid.store.persistence;

public interface Persistence {

    void putProduct(ProductData productData) throws PersistenceException;

    ProductData getProduct(String name) throws PersistenceException;

    void putCart(CartData cartData) throws PersistenceException;

    CartData getCart(int cartId) throws PersistenceException;

}
