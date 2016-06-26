package com.luxoft.training.solid.store;

public class MockReceiptFactory implements ReceiptFactory {
    @Override
    public Receipt createReceipt(Format format) {
        return new Receipt() {
            @Override
            public void addProduct(String name, int count, double price, double priceForAll) {

            }

            @Override
            public void setTotalPrice(double totalPrice) {

            }

            @Override
            public void addDelivery(double cost) {

            }
        };
    }
}
