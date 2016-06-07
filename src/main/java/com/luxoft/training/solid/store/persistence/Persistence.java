package com.luxoft.training.solid.store.persistence;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;

public interface Persistence {

    public void putProduct(String name, Product product);

    public Product getProduct(String name);

    public void putCart(int cartId, Cart cart);

    public Cart getCart(int cartId);
}
