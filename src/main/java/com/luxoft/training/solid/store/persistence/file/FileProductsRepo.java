package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.NotEnoughInStockException;
import com.luxoft.training.solid.store.ProductData;
import com.luxoft.training.solid.store.exception.ProductNotFoundException;
import com.luxoft.training.solid.store.provisioning.ProductsRepo;

import java.io.Serializable;

public class FileProductsRepo implements ProductsRepo {

    private final FilePersistence filePersistence;

    public FileProductsRepo(FilePersistence filePersistence) {
        this.filePersistence = filePersistence;
    }

    @Override
    public ProductData takeProduct(String name, int count) throws ProductNotFoundException, NotEnoughInStockException {
        SerializableProduct p = getProduct(name);
        int remainingCount = p.count - count;
        if (remainingCount < 0) {
            throw new NotEnoughInStockException(p.asProductData(), count);
        }
        p.count = remainingCount;
        filePersistence.getStock().put(name, p);
        filePersistence.save();
        return p.asProductData();
    }

    @Override
    public void addProduct(String name, double price, int count) {
        SerializableProduct newProduct;
        try {
            SerializableProduct existingProduct = getProduct(name);
            newProduct = new SerializableProduct(name, price, existingProduct.count + count);
        } catch (ProductNotFoundException e) {
            newProduct = new SerializableProduct(name, price, count);
        }
        filePersistence.getStock().put(name, newProduct);
        filePersistence.save();
    }

    private SerializableProduct getProduct(String name) {
        SerializableProduct product = filePersistence.getStock().get(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        return product;
    }

    static class SerializableProduct implements Serializable {

        private static final long serialVersionUID = -7588980448693010399L;

        private String name;
        private double price;
        private int count;

        public SerializableProduct(String name, double price, int count) {
            this.name = name;
            this.price = price;
            this.count = count;
        }

        public SerializableProduct(ProductData p) {
            this(p.getName(), p.getPrice(), p.getCount());
        }

        public ProductData asProductData() {
            return new ProductData(name, price, count);
        }
    }
}
