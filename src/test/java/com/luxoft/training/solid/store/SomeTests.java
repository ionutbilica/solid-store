package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.CartNotFoundException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.training.solid.store.Product.VAT;
import static org.junit.Assert.assertEquals;

public class SomeTests {

    public static final double BREAD_PRICE = 4;
    public static final double BREAD_PRICE_WITH_VAT = BREAD_PRICE + BREAD_PRICE * VAT;
    public static final double WINE_PRICE = 8;
    public static final double WINE_PRICE_WITH_VAT = WINE_PRICE + WINE_PRICE * VAT;
    public static final String BREAD_ID = "Bread";
    public static final String WINE_ID = "Wine";
    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store(createProducts());
        cartId = store.createNewCart();
    }

    @Test
    public void testEmptyCart() throws Exception {
        assertEquals("Cart should have no product.", 0, store.getProductsCountInCart(cartId));
        assertEquals("Cart total should be the price of the one product.", 0, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testSingleProduct() {
        store.addProductToCart(BREAD_ID, cartId);
        assertEquals("Cart should have one product.", 1, store.getProductsCountInCart(cartId));
        assertEquals("Cart total should be 0.", BREAD_PRICE_WITH_VAT, store.getCartTotal(cartId), 0.1);
    }

    @Test
    public void testTwoProduct() {
        store.addProductToCart(BREAD_ID, cartId);
        store.addProductToCart(WINE_ID, cartId);
        assertEquals("Cart should have two products.", 2, store.getProductsCountInCart(cartId));
        assertEquals("Cart total should be the sum of both products.", BREAD_PRICE_WITH_VAT + WINE_PRICE_WITH_VAT, store.getCartTotal(cartId), 0.1);
    }

    @Test(expected = CartNotFoundException.class)
    public void testNonexistentCart() throws Exception {
        store.addProductToCart("Bread", 777);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testNonexistentProduct() throws Exception {
        store.addProductToCart("NotFound", cartId);
    }

    private Map<String, Product> createProducts() {
        Map<String, Product> products = new HashMap<>();
        products.put(BREAD_ID, new Product(BREAD_ID, BREAD_PRICE));
        products.put(WINE_ID, new Product(WINE_ID, WINE_PRICE));
        return products;
    }
}
