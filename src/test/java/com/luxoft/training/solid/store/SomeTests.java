package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.training.solid.store.Product.VAT;
import static com.luxoft.training.solid.store.TestStock.*;
import static org.junit.Assert.assertEquals;

public class SomeTests {

    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store(new TestStock().getProducts(), new StoreMoneyAccount());
        cartId = store.createNewCart();
    }

    @Test
    public void testEmptyCart() throws Exception {
        assertEquals("Cart total should be the price of the one product.", 0, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testSingleProduct() {
        store.addProductToCart(BREAD_ID, cartId);
        assertEquals("Cart total should be 0.", BREAD_PRICE_WITH_VAT, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testTwoProduct() {
        store.addProductToCart(BREAD_ID, cartId);
        store.addProductToCart(WINE_ID, cartId);
        assertEquals("Cart total should be the sum of both products.", BREAD_PRICE_WITH_VAT + WINE_PRICE_WITH_VAT, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testTwoOfOneAndAnotherProduct() {
        store.addProductToCart(BREAD_ID, 2, cartId);
        store.addProductToCart(WINE_ID, cartId);
        assertEquals("Cart total should be the sum of both products.", 2 * BREAD_PRICE_WITH_VAT + WINE_PRICE_WITH_VAT, store.getCartTotal(cartId), 0.1);
    }

    @Test(expected = CartNotFoundException.class)
    public void testNonexistentCart() throws Exception {
        store.addProductToCart("Bread", 777);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testNonexistentProduct() throws Exception {
        store.addProductToCart("NotFound", cartId);
    }
}
