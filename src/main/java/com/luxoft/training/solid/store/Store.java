package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private final Map<String, Product> stock;
    private double cash;
    private final Map<Integer, Cart> carts;
    private int latestCartId;

    public Store() {
        this.stock = new HashMap<>();
        cash = 0;
        carts = new HashMap<Integer, Cart>();
        latestCartId = 0;
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

    public String pay(int cartId) {
        Cart cart = getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        cash += moneyFromTheClient;
        return cart.getReceipt();
    }

    public double getCashAmount() {
        return cash;
    }

    private Product getProduct(String productId, int count) {
        Product productInStock = stock.get(productId);
        if (productInStock == null) {
            throw new ProductNotFoundException(productId);
        }
        Product productInCart = productInStock.takeSome(count);
        return productInCart;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }

    public void addProductToStock(String name, double price, int count) {
        Product p = new Product(name, price, count);
        stock.put(name, p);
    }
}
