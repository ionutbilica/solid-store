package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.persistence.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePersistence implements Persistence {

    private static final File FILE = new File("store.save");

    private Map<String, SerializableProduct> stock;

    private Map<Integer, SerializableCart> carts;

    public FilePersistence() {
        if (FILE.exists()) {
            load();
        } else {
            stock = new HashMap<>();
            carts = new HashMap<>();
        }
    }

    @Override
    public void putProduct(ProductData productData) {
        stock.put(productData.getName(), new SerializableProduct(productData));
        save();
    }

    @Override
    public ProductData getProduct(String name) {
        SerializableProduct serializableProduct = stock.get(name);
        if (serializableProduct == null) {
            throw new ProductNotFoundException(name);
        }
        return serializableProduct.asProductData();
    }

    @Override
    public void putCart(CartData cartData) {
        carts.put(cartData.getId(), new SerializableCart(cartData));
        save();
    }

    @Override
    public CartData getCart(int cartId) {
        SerializableCart serializableCart = carts.get(cartId);
        if (serializableCart == null) {
            throw new CartNotFoundException(cartId);
        }
        return serializableCart.asCartData();
    }

    private void save() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(stock);
            out.writeObject(carts);
        }catch(IOException e) {
            throw new PersistenceException(e);
        }
    }

    private void load() {
        try (FileInputStream fileIn = new FileInputStream(FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stock = (Map<String, SerializableProduct>) in.readObject();
            carts = (Map<Integer, SerializableCart>) in.readObject();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    private static class SerializableProduct implements Serializable {

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

    private static class SerializableCart implements Serializable {

        private static final long serialVersionUID = -7588980448693010000L;
        
        private int id;
        private List<SerializableProduct> products;
        private boolean hasDelivery;

        public SerializableCart(int id, List<ProductData> productsData, boolean hasDelivery) {
            this.id = id;
            this.products = new ArrayList<>();
            for (ProductData p : productsData) {
                this.products.add(new SerializableProduct(p));
            }
            this.hasDelivery = hasDelivery;
        }

        public SerializableCart(CartData d) {
            this(d.getId(), d.getProductsData(), d.isHasDelivery());
        }
        
        public CartData asCartData() {
            List<ProductData> productsData = new ArrayList<>(products.size());
            for (SerializableProduct p : this.products) {
                productsData.add(p.asProductData());
            }
            return new CartData(id, productsData, hasDelivery);            
        }
    }

}
