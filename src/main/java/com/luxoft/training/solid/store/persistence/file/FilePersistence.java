package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.CartData;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.persistence.Persistence;

import java.io.*;
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
        return stock.get(name);
    }

    @Override
    public void putCart(int cartId, Cart cart) {
        carts.put(cartId, new SerializableCartData(cart.getData()));
        save();
    }

    @Override
    public Cart getCart(int cartId) {
        return new Cart(carts.get(cartId));
    }

    private void save() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(stock);
//            out.writeObject(carts);
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() {
        try (FileInputStream fileIn = new FileInputStream(FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stock = (Map<String, SerializableProduct>) in.readObject();
//            carts = (Map<Integer, SerializableCartData>) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        FilePersistence p = new FilePersistence();
        p.putProduct("ceva", new Product("ceceva", 123, 456));
        p.putProduct("cevava", new Product("ceceva", 123, 456));
//        p.putCart(123, new Cart(123));
        FilePersistence pp = new FilePersistence();
        Product ceva = pp.getProduct("ceva");
        System.out.println(ceva);
//        Cart cart = p.getCart(123);
//        System.out.println(cart.getReceipt());
    }

    public static class SerializableProduct extends Product implements Serializable {

        private static final long serialVersionUID = -7588980448693010399L;

        public SerializableProduct() {
            super(null, 0, 0);
        }

        public SerializableProduct(Product p) {
            super(p.getName(), p.getPrice(), p.getCount());
        }
    }

    private static class SerializableCartData extends CartData implements Serializable {

        private static final long serialVersionUID = -7588980448693010000L;

        public SerializableCartData() {
            super(0, null, false);
        }

        public SerializableCartData(CartData c) {
            super(c.getId(), c.getProducts(), c.isHasDelivery());
        }
    }
}
