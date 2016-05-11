package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private Map<String, Product> stock;
    private final StoreMoneyAccount account;
    private final Map<Integer, Cart> carts;
    private int latestCartId;
    private enum Delivery {STANDARD, FAST, PICKUP};

    public Store(Map<String, Product> stock, StoreMoneyAccount account) {
        this.account = account;
        this.carts = new HashMap<Integer, Cart>();
        latestCartId = 0;
        this.stock = stock;
    }

    public int createNewCart() {
        latestCartId++;
        carts.put(latestCartId, new Cart(latestCartId));
        return latestCartId;
    }

    public void addProductToCart(String productId, int cartId) {
        addProductToCart(productId, 1, cartId);
    }

    public void addProductToCart(String productId, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = getProduct(productId, count);
        cart.addProduct(product);
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    public void pay(int cartId) {
        Cart cart = getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        // Play pretend we're taking cash from the client's account.
        account.receiveMoney(moneyFromTheClient);
    }

    private Product getProduct(String productId, int count) {
        Product productInStock = stock.get(productId);
        if (productInStock == null) {
            throw new ProductNotFoundException(productId);
        }
        Product productInCart = productInStock.getSome(count);
        return productInCart;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }
}
