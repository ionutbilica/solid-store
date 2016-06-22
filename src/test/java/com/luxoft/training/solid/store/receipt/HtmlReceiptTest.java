package com.luxoft.training.solid.store.receipt;

import com.luxoft.training.solid.store.Store;
import com.luxoft.training.solid.store.TestStock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HtmlReceiptTest {

    private Store store;
    private int cartId;

    @Before
    public void beforeTest() {
        store = new Store();
        cartId = store.createNewCart();
    }

    @Test
    public void testEmptyCart() {
        String receipt = store.pay(cartId, ReceiptFactory.Format.HTML.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Empty receipt not as expected."
                , "Our StoreReceipt no.: not able to test this.\n" +
                "<div><b>Total</b>: 0.0</div>\n" +
                "<div><b>Date</b>: not able to test this.</div>\n"
                , receipt);
        System.out.println(receipt);
    }

    @Test
    public void testTwoProducts() {
        new TestStock().insertIntoStore(store);
        store.addProductToCart(TestStock.BREAD_NAME, cartId);
        store.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        String receipt = store.pay(cartId, ReceiptFactory.Format.HTML.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Receipt for two products not as expected."
        , "Our StoreReceipt no.: not able to test this.\n" +
                        "<div><b>Bread</b>: 1 x 5.0 = 5.0</div>\n" +
                        "<div><b>Wine</b>: 2 x 10.0 = 20.0</div>\n" +
                        "<div><b>Total</b>: 25.0</div>\n" +
                        "<div><b>Date</b>: not able to test this.</div>\n"
                ,receipt
        );
    }

    @Test
    public void testWithDelivery() {
        new TestStock().insertIntoStore(store);
        store.addProductToCart(TestStock.BREAD_NAME, cartId);
        store.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        store.addDeliveryToCart(cartId);
        String receipt = store.pay(cartId, ReceiptFactory.Format.HTML.toString());
        receipt = removeUntestableInfo(receipt);
        Assert.assertEquals("Receipt for two products not as expected."
                , "Our StoreReceipt no.: not able to test this.\n" +
                        "<div><b>Bread</b>: 1 x 5.0 = 5.0</div>\n" +
                        "<div><b>Wine</b>: 2 x 10.0 = 20.0</div>\n" +
                        "<div><b>Delivery</b>: 12.0</div>\n" +
                        "<div><b>Total</b>: 37.0</div>\n" +
                        "<div><b>Date</b>: not able to test this.</div>\n"
                ,receipt
        );
    }

    private String removeUntestableInfo(String receipt) {
        receipt = receipt.replaceAll("Our StoreReceipt no.: .*\\n", "Our StoreReceipt no.: not able to test this.\n");
        receipt = receipt.replaceAll("Date</b>: .*</div>\\n", "Date</b>: not able to test this.</div>\n");
        return receipt;
    }
}
