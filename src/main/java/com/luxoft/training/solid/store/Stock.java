package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.ProductNotFoundException;

public interface Stock {

    ProductData takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException;
}
