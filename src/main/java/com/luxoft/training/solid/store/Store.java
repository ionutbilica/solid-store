package com.luxoft.training.solid.store;

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
        Cart cart = getCart(cartId);
        Product product = new Product(stock.takeProduct(name, count));
        cart.addProduct(product);
        cartsRepo.saveCart(cart.getData());
    }

    private Cart getCart(int cartId) {
        CartData cartData = cartsRepo.getCart(cartId);
        Cart cart = new Cart(cartData.getId());
        if (cartData.isHasDelivery()) cart.addDelivery();
        for (ProductData pd : cartData.getProducts()) {
            cart.addProduct(new Product(pd));
        }
        return cart;
    }

    public double getCartTotal(int cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }

    public void addDeliveryToCart(int cartId) {
        Cart cart = getCart(cartId);
        cart.addDelivery();
        cartsRepo.saveCart(cart.getData());
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
}
