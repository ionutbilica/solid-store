package com.luxoft.training.solid.store.receipt;

import com.luxoft.training.solid.store.*;
import com.luxoft.training.solid.store.MemStock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextReceiptTest {

    private Sales sales;
    private int cartId;
    private Stock stock;

    @Before
    public void beforeTest() throws ParseException {
        Date fixedDate = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse("10-12-2015 12:33:44");
        MockClock mockClock = new MockClock(fixedDate);
        MockIdGenerator receiptNoGenerator = new MockIdGenerator(33);
        ReceiptFactory receiptFactory = new ConcreteReceiptFactory(receiptNoGenerator, mockClock);
        stock = new MemStock();
        sales = new Store(new TestStock(), new CartsRepo(new MockIdGenerator(1)), receiptFactory);

        cartId = sales.createNewCart();
    }

    @Test
    public void testEmptyCart() {
        String receipt = sales.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        Assert.assertEquals("Empty receipt not as expected."
                , "Our StoreReceipt no.: 33\n" +
                "Total: 0.0\n" +
                "Date: 10-12-2015 12:33:44\n"
                , receipt);
    }

    @Test
    public void testTwoProducts() {
        sales.addProductToCart(TestStock.BREAD_NAME, cartId);
        sales.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        String receipt = sales.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        Assert.assertEquals("Receipt for two products not as expected."
        , "Our StoreReceipt no.: 33\n" +
                        "Bread: 1 x 5.0 = 5.0\n" +
                        "Wine: 2 x 10.0 = 20.0\n" +
                        "Total: 25.0\n" +
                        "Date: 10-12-2015 12:33:44\n"
                ,receipt
        );
    }

    @Test
    public void testWithDelivery() {
        sales.addProductToCart(TestStock.BREAD_NAME, cartId);
        sales.addProductToCart(TestStock.WINE_NAME, 2, cartId);
        sales.addDeliveryToCart(cartId);
        String receipt = sales.pay(cartId, ReceiptFactory.Format.TEXT.toString());
        Assert.assertEquals("Receipt for two products not as expected."
                , "Our StoreReceipt no.: 33\n" +
                        "Bread: 1 x 5.0 = 5.0\n" +
                        "Wine: 2 x 10.0 = 20.0\n" +
                        "Delivery: 12.0\n" +
                        "Total: 37.0\n" +
                        "Date: 10-12-2015 12:33:44\n"
                ,receipt
        );
    }
}
