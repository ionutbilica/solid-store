package com.luxoft.training.solid.store.persistence.file;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import org.junit.Assert;
import org.junit.Test;

public class FilePersistenceTest {
    
    @Test
    public void test() {
        FilePersistence p = new FilePersistence();
        Product product = new Product("wine", 100, 5);
        p.putProduct("wine", product);
        Cart cart = new Cart(123);
        cart.addProduct(product);
        cart.addDelivery();
        p.putCart(123, cart);
        
        FilePersistence p2 = new FilePersistence();
        Product actualWine = p2.getProduct("wine");
        Assert.assertEquals("Product not persisted", product, actualWine);
        Cart actualCart = p2.getCart(123);
        Assert.assertEquals("Cart not persisted", cart, actualCart);        
    }    

}