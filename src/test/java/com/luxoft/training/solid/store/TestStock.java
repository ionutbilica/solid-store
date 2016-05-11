package com.luxoft.training.solid.store;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.training.solid.store.Product.VAT;

public class TestStock {

    public static final double BREAD_PRICE = 4;
    public static final double BREAD_PRICE_WITH_VAT = BREAD_PRICE + BREAD_PRICE * VAT;
    public static final double WINE_PRICE = 8;
    public static final double WINE_PRICE_WITH_VAT = WINE_PRICE + WINE_PRICE * VAT;
    public static final String BREAD_ID = "Bread";
    public static final String WINE_ID = "Wine";

    public Map<String, Product> getProducts() {
        Map<String, Product> products = new HashMap<>();
        products.put(BREAD_ID, new Product(BREAD_ID, BREAD_PRICE, 100));
        products.put(WINE_ID, new Product(WINE_ID, WINE_PRICE, 100));
        return products;
    }
}
