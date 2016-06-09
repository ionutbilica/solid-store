package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.persistence.*;

import java.util.HashMap;
import java.util.Map;

public class InMemPersistence implements Persistence {
    
    private final Map<String, ProductData> products;
    
    private final Map<Integer, CartData> carts;

    public InMemPersistence() {
        products = new HashMap<>();
        carts = new HashMap<>();
    }

    @Override
    public void putProduct(ProductData productData) throws PersistenceException {
        products.put(productData.getName(), productData);        
    }

    @Override
    public ProductData getProduct(String name) throws PersistenceException {
        ProductData productData = products.get(name);
        if (productData == null) {
            throw new ProductNotFoundException(name);    
        }
        return productData;
    }

    @Override
    public void saveCart(CartData cartData) throws PersistenceException {
        carts.put(cartData.getId(), cartData);
    }

    @Override
    public CartData getCart(int cartId) throws PersistenceException {
        CartData cartData = carts.get(cartId);
        if (cartData == null) {
            throw new CartNotFoundException(cartId);
        }
        return cartData;
    }
}
