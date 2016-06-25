package com.luxoft.training.solid.store;

import com.luxoft.training.solid.store.exception.NotEnoughInStockException;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;

public interface Stock {

    Product takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException;
}
