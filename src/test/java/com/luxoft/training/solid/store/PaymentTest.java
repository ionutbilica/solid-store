package com.luxoft.training.solid.store;

import org.junit.Before;
import org.junit.Test;

import static com.luxoft.training.solid.store.TestStock.BREAD_ID;
import static com.luxoft.training.solid.store.TestStock.BREAD_PRICE_WITH_VAT;
import static org.junit.Assert.assertEquals;

public class PaymentTest {

    private Store store;
    private int cartId;
    private StoreMoneyAccount storeAccount;

    @Before
    public void beforeTest() {
        storeAccount = new StoreMoneyAccount();
        store = new Store(new TestStock().getProducts(), storeAccount);
        cartId = store.createNewCart();
    }

    @Test
    public void testPayment() throws Exception {
        store.addProductToCart(BREAD_ID, cartId);
        store.pay(cartId);
        assertEquals("Cart total should be 0.", BREAD_PRICE_WITH_VAT, storeAccount.getAmount(), 0.1);
    }
}
