package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.NotEnoughInStockException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import com.luxoft.training.solid.store.provisioning.ProductsRepo;

public class TestStock extends ProductsRepo {

    public static final double BREAD_PRICE = 5;
    public static final double WINE_PRICE = 10;
    public static final String BREAD_NAME = "Bread";
    public static final String WINE_NAME = "Wine";

    public TestStock() {
        super();
        addProduct(BREAD_NAME, BREAD_PRICE, 100);
        addProduct(WINE_NAME, WINE_PRICE, 100);
    }
}
