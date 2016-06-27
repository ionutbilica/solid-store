package com.luxoft.training.solid.store;

public interface CartsRepo {
    int createNewCart();
    CartData getCart(int cartId);
    void saveCart(CartData cartData);
}
