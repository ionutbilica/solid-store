package com.luxoft.training.solid.store;

public interface Receipt {

    void addProduct(String name, int count, double price, double priceForAll);

    void setTotalPrice(double totalPrice);

    void addDelivery(double cost);

    String toString();
}
