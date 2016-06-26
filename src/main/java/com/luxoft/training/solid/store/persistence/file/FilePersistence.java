package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.provisioning.PersistenceException;

import java.io.*;
import java.util.Map;

public class FilePersistence implements Serializable {

    private static final long serialVersionUID = -7588980448693000392L;

    private static final File FILE = new File("store.save");

    private Map<String, FileProductsRepo.SerializableProduct> stock;

    public Map<String, FileProductsRepo.SerializableProduct> getStock() {
        return stock;
    }

    public void setStock(Map<String, FileProductsRepo.SerializableProduct> stock) {
        this.stock = stock;
    }

    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        }catch(IOException e) {
            throw new PersistenceException(e);
        }
    }

    private void load() {
        try (FileInputStream fileIn = new FileInputStream(FILE);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            FilePersistence stock = (FilePersistence) in.readObject();
//            carts = (Map<Integer, SerializableCart>) in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            throw new PersistenceException(e);
        }
    }
}
