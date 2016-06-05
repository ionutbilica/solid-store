package com.luxoft.training.solid.store;

import static com.luxoft.training.solid.store.Product.VAT;

public class TestStock {

    public static final double BREAD_PRICE = 4;
    public static final double BREAD_PRICE_WITH_VAT = BREAD_PRICE + BREAD_PRICE * VAT;
    public static final double WINE_PRICE = 8;
    public static final double WINE_PRICE_WITH_VAT = WINE_PRICE + WINE_PRICE * VAT;
    public static final String BREAD_NAME = "Bread";
    public static final String WINE_NAME = "Wine";

    public void insertIntoStore(Store store) {
        store.addProductToStock(BREAD_NAME, BREAD_PRICE, 100);
        store.addProductToStock(WINE_NAME, WINE_PRICE, 100);
    }
}
