package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store implements Sales {

    private final Stock stock;
    private final ReceiptFactory receiptFactory;
    private double cash;
    private final Map<Integer, Cart> carts;
    private int latestCartId;

    public Store(Stock stock, ReceiptFactory receiptFactory) {
        this.stock = stock;
        this.receiptFactory = receiptFactory;
        cash = 0;
        carts = new HashMap<Integer, Cart>();
        latestCartId = 0;
    }

    @Override
    public int createNewCart() {
        latestCartId++;
        carts.put(latestCartId, new Cart(latestCartId, receiptFactory));
        return latestCartId;
    }

    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = getCart(cartId);
        Product product = new Product(stock.takeProduct(name, count));
        cart.addProduct(product);
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    public void addDeliveryToCart(int cartId) {
        Cart cart = getCart(cartId);
        cart.addDelivery();
    }

    public String pay(int cartId, String formatName) {
        Cart cart = getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        cash += moneyFromTheClient;
        Receipt receipt = receiptFactory.createReceipt(ReceiptFactory.Format.valueOf(formatName));
        return cart.fillInReceipt(receipt);
    }

    public double getCashAmount() {
        return cash;
    }

    private Cart getCart(int cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw  new CartNotFoundException(cartId);
        }
        return cart;
    }
}
