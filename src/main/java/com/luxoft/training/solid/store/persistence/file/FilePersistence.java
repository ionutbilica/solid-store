package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.CartData;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.persistence.Persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePersistence implements Persistence {

    private static final File FILE = new File("store.save");

    private Map<String, SerializableProduct> stock;

    private Map<Integer, SerializableCartData> carts;

    public FilePersistence() {
        if (FILE.exists()) {
            load();
        } else {
            stock = new HashMap<>();
            carts = new HashMap<>();
        }
    }

    @Override
    public void putProduct(String name, Product product) {
        stock.put(name, new SerializableProduct(product));
        save();
    }

    @Override
    public Product getProduct(String name) {
        return stock.get(name).asProduct();
    }

    @Override
    public void putCart(int cartId, Cart cart) {
        CartData data = cart.getData();
        carts.put(cartId, new SerializableCartData(data));
        save();
    }

    @Override
    public Cart getCart(int cartId) {
        return new Cart(carts.get(cartId).asCartData());
    }

    private void save() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(stock);
            out.writeObject(carts);
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() {
        try (FileInputStream fileIn = new FileInputStream(FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stock = (Map<String, SerializableProduct>) in.readObject();
            carts = (Map<Integer, SerializableCartData>) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

        public SerializableProduct(Product p) {
            this(p.getName(), p.getPrice(), p.getCount());
        }
        
        public Product asProduct() {
            return new Product(name, price, count);
        }
    }

    private static class SerializableCartData implements Serializable {

        private static final long serialVersionUID = -7588980448693010000L;
        
        private int id;
        private List<SerializableProduct> products;
        private boolean hasDelivery;

        public SerializableCartData(int id, List<Product> products, boolean hasDelivery) {
            this.id = id;
            this.products = new ArrayList<>();
            for (Product p : products) {
                this.products.add(new SerializableProduct(p));
            }
            this.hasDelivery = hasDelivery;
        }

        public SerializableCartData(CartData d) {
            this(d.getId(), d.getProducts(), d.isHasDelivery());
        }
        
        public CartData asCartData() {
            List<Product> products = new ArrayList<>();
            for (SerializableProduct p : this.products) {
                products.add(p.asProduct());
            }
            return new CartData(id, products, hasDelivery);            
        }
    }

}
