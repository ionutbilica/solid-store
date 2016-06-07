package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.NotEnoughInStockException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import com.luxoft.training.solid.store.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class Store {


    private final Persistence persistence;
    private double cash;

    private int latestCartId;

    public Store(Persistence persistence) {
        this.persistence = persistence;
        cash = 0;
        latestCartId = 0;
    }

    public int createNewCart() {
        latestCartId++;
        persistence.putCart(latestCartId, new Cart(latestCartId));
        return latestCartId;
    }

    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = takeFromStock(name, count);
        cart.addProduct(product);
    }

    private Product takeFromStock(String name, int count) {
        Product productInStock = findProductInStock(name);
        removeProductFromStock(productInStock, count);
        return new Product(productInStock.getName(), productInStock.getPrice(), count);
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    public void addDeliveryToCart(int cartId) {
        Cart cart = getCart(cartId);
        cart.addDelivery();
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

    private Cart getCart(int cartId) {
        Cart cart = persistence.getCart(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }

    public void addProductToStock(String name, double price, int count) {
        Product p;
        try {
            p = findProductInStock(name);
            p = new Product(name, price, p.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new Product(name, price, count);
        }
        persistence.putProduct(name, p);
    }

    private Product findProductInStock(String name) {
        Product productInStock = persistence.getProduct(name);
        if (productInStock == null) {
            throw new ProductNotFoundException(name);
        }
        return productInStock;
    }

    public void removeProductFromStock(String name, int countToRemove) {
        Product p = findProductInStock(name);
        removeProductFromStock(p, countToRemove);
    }

    private void removeProductFromStock(Product p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new Product(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        persistence.putProduct(p.getName(), p);
    }
}
