package com.luxoft.training.solid.store.persistenceimpl.h2;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.persistenceimpl.file.FilePersistence;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class H2PersistenceTest {
    
    @Test
    public void test() throws SQLException, ClassNotFoundException, IOException {
        H2Persistence p = new H2Persistence();
        Product product = new Product("wine", 100, 5);
        p.putProduct(product.getData());
        Cart cart = new Cart(123);
        cart.addProduct(product);
        cart.addDelivery();
        p.saveCart(cart.getData());
        p.close();

        H2Persistence p2 = new H2Persistence();
        Product actualWine = new Product(p2.getProduct("wine"));
        Assert.assertEquals("Product not persisted", product, actualWine);
        Cart actualCart = new Cart(p2.getCart(123));
        Assert.assertEquals("Cart not persisted", cart, actualCart);
        p.close();
    }    

}