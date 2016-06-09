package com.luxoft.training.solid.store.persistenceimpl.file;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import org.junit.Assert;
import org.junit.Test;

public class FilePersistenceTest {
    
    @Test
    public void test() {
        FilePersistence p = new FilePersistence();
        Product product = new Product("wine", 100, 5);
        p.putProduct(product.getData());
        Cart cart = new Cart(123);
        cart.addProduct(product);
        cart.addDelivery();
        p.saveCart(cart.getData());
        
        FilePersistence p2 = new FilePersistence();
        Product actualWine = new Product(p2.getProduct("wine"));
        Assert.assertEquals("Product not persisted", product, actualWine);
        Cart actualCart = new Cart(p2.getCart(123));
        Assert.assertEquals("Cart not persisted", cart, actualCart);        
    }    

}