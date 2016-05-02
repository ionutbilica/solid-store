package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private final Map<Integer, Cart> carts;
    private int latestCartId;
    private Map<String, Product> products;
    public enum Delivery {STANDARD, FAST, PICKUP};

    public Store(Map<String, Product> products) {
        this.carts = new HashMap<Integer, Cart>();
        latestCartId = 0;
        this.products = products;
    }

    public int createNewCart() {
        latestCartId++;
        carts.put(latestCartId, new Cart(latestCartId));
        return latestCartId;
    }

    public void addProductToCart(String productId, int cartId) {
        Cart cart = getCart(cartId);
        Product product = getProduct(productId);
        cart.addProduct(product);
    }

    public int getProductsCountInCart(int cartId) {
        return getCart(cartId).getProductsCount();
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotal();
    }

    private Product getProduct(String productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }
}
