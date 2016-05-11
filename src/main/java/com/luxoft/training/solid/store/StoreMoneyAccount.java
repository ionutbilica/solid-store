package com.luxoft.training.solid.store;

public class StoreMoneyAccount {

    private double amount;

    public void receiveMoney(double receivedAmount) {
        amount += receivedAmount;
    }

    public double getAmount() {
        return amount;
    }
}
