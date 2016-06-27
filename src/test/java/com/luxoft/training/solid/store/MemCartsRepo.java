package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class MemCartsRepo implements CartsRepo {

    private final IdGenerator idGenerator;
    private final Map<Integer, CartData> carts;

    public MemCartsRepo(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        carts = new HashMap<>();
    }

    @Override 
    public int createNewCart() {
        int id = idGenerator.generateId();
        carts.put(id, new CartData(id));
        return id;
    }

    @Override 
    public CartData getCart(int cartId) {
        CartData cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }

    @Override
    public void saveCart(CartData cartData) {
        carts.put(cartData.getId(), cartData);
    }


}
