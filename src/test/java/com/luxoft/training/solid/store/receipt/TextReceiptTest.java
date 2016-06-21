package com.luxoft.training.solid.store.receipt;

import com.luxoft.training.solid.store.Store;
import com.luxoft.training.solid.store.TestStock;
import com.luxoft.training.solid.store.receipt.ReceiptFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextReceiptTest {

    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store();
        cartId = store.createNewCart();
    }

    @Test
    public void testEmptyCart() {
        String receipt = store.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Empty receipt not as expected."
                , "Our StoreReceipt no.: not able to test this.\n" +
                "Total: 0.0\n" +
                "Date: not able to test this.\n"
                , receipt);
        System.out.println(receipt);
    }

    @Test
    public void testTwoProducts() {
        new TestStock().insertIntoStore(store);
        store.addProductToCart(TestStock.BREAD_NAME, cartId);
        store.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        String receipt = store.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Receipt for two products not as expected."
        , "Our StoreReceipt no.: not able to test this.\n" +
                        "Bread: 1 x 5.0 = 5.0\n" +
                        "Wine: 2 x 10.0 = 20.0\n" +
                        "Total: 25.0\n" +
                        "Date: not able to test this.\n"
                ,receipt
        );
    }

    @Test
    public void testWithDelivery() {
        new TestStock().insertIntoStore(store);
        store.addProductToCart(TestStock.BREAD_NAME, cartId);
        store.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        store.addDeliveryToCart(cartId);
        String receipt = store.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Receipt for two products not as expected."
                , "Our StoreReceipt no.: not able to test this.\n" +
                        "Bread: 1 x 5.0 = 5.0\n" +
                        "Wine: 2 x 10.0 = 20.0\n" +
                        "Delivery: 12.0\n" +
                        "Total: 37.0\n" +
                        "Date: not able to test this.\n"
                ,receipt
        );
    }

    private String removeUntestableInfo(String receipt) {
        receipt = receipt.replaceAll("Our StoreReceipt no.: .*\\n", "Our StoreReceipt no.: not able to test this.\n");
        receipt = receipt.replaceAll("Date: .*\\n", "Date: not able to test this.\n");
        return receipt;
    }
}
