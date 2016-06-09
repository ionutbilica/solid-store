package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.persistence.Persistence;
import com.luxoft.training.solid.store.persistence.ProductNotFoundException;

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
        persistence.saveCart(new Cart(latestCartId).getData());
        return latestCartId;
    }

    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = takeFromStock(name, count);
        cart.addProduct(product);
        persistence.saveCart(cart.getData());
    }

    private Product takeFromStock(String name, int count) {
        Product productInStock = new Product(persistence.getProduct(name));
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
        persistence.saveCart(cart.getData());
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
        return new Cart(persistence.getCart(cartId));
    }

    public void addProductToStock(String name, double price, int count) {
        Product p;
        try {
            Product oldProduct = new Product(persistence.getProduct(name));
            p = new Product(name, price, oldProduct.getCount() + count);
        } catch (ProductNotFoundException e) {
            p = new Product(name, price, count);
        }
        persistence.putProduct(p.getData());
    }

    public void removeProductFromStock(String name, int countToRemove) {
        Product p = new Product(persistence.getProduct(name));
        removeProductFromStock(p, countToRemove);
    }

    private void removeProductFromStock(Product p, int countToRemove) {
        if (countToRemove > p.getCount()) {
            throw new NotEnoughInStockException(p, countToRemove);
        }
        p = new Product(p.getName(), p.getPrice(), p.getCount() - countToRemove);
        persistence.putProduct(p.getData());
    }
}
