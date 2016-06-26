package com.luxoft.training.solid.store.persistence.file;

import java.util.Map;

public class SerializableData {
    private final Map<String, FileProductsRepo.SerializableProduct> stock;

    public SerializableData(Map<String, FileProductsRepo.SerializableProduct> stock) {
        this.stock = stock;
    }

    public Map<String, FileProductsRepo.SerializableProduct> getStock() {
        return stock;
    }

    //    private Map<Integer, SerializableCart> carts;
}
