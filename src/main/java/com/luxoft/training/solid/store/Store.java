package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class Store implements Sales {

    private final Stock stock;
    private final CartsRepo cartsRepo;
    private final ReceiptFactory receiptFactory;
    private double cash;

    public Store(Stock stock, CartsRepo cartsRepo, ReceiptFactory receiptFactory) {
        this.stock = stock;
        this.cartsRepo = cartsRepo;
        this.receiptFactory = receiptFactory;
        cash = 0;
    }

    @Override
    public int createNewCart() {
        return cartsRepo.createNewCart();
    }

    public void addProductToCart(String name, int cartId) {
        addProductToCart(name, 1, cartId);
    }

    public void addProductToCart(String name, int count, int cartId) {
        Cart cart = cartsRepo.getCart(cartId);
        Product product = new Product(stock.takeProduct(name, count));
        cart.addProduct(product);
    }

    public double getCartTotal(int cartId) {
        Cart cart = cartsRepo.getCart(cartId);
        return cart.getTotalPrice();
    }

    public void addDeliveryToCart(int cartId) {
        Cart cart = cartsRepo.getCart(cartId);
        cart.addDelivery();
    }

    public String pay(int cartId, String formatName) {
        Cart cart = cartsRepo.getCart(cartId);
        double moneyFromTheClient = cart.getTotalPrice();
        cash += moneyFromTheClient;
        Receipt receipt = receiptFactory.createReceipt(ReceiptFactory.Format.valueOf(formatName));
        return cart.fillInReceipt(receipt);
    }

    public double getCashAmount() {
        return cash;
    }
}
