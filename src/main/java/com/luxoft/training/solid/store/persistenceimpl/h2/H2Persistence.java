package com.luxoft.training.solid.store.persistenceimpl.h2;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.persistence.*;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2Persistence implements Persistence, Closeable {

    private Connection connection;

    public H2Persistence() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

        String createProductTableSql = "CREATE TABLE IF NOT EXISTS PRODUCT"
                + "  (name           VARCHAR(30),"
                + "   price            FLOAT,"
                + "   count          INTEGER,"
                + " PRIMARY KEY (name)"
                + ")";
        connection.createStatement().execute(createProductTableSql);

        String createCartTableSql = "CREATE TABLE IF NOT EXISTS CART"
                + "  (id           INTEGER,"
                + "   delivery            BOOLEAN,"
                + " PRIMARY KEY (id)"
                + ")";
        connection.createStatement().execute(createCartTableSql);

        String createCartProductTableSql = "CREATE TABLE IF NOT EXISTS CART_PRODUCT"
                + "  (cart_id           INTEGER,"
                + "   product_name             VARCHAR(30),"
                + " PRIMARY KEY (cart_id,product_name)"
                + ")";
        connection.createStatement().execute(createCartProductTableSql);
    }
    
    @Override
    public void putProduct(ProductData productData) {
        try {
            getProduct(productData.getName());
            updateProduct(productData);
        } catch (ProductNotFoundException e) {
            insertProduct(productData);                
        }
    }

    private void insertProduct(ProductData productData) {
        executeSql("INSERT INTO PRODUCT VALUES ('" + productData.getName() + "', " + productData.getPrice() + " , " + productData.getCount() + ")");
    }

    private void updateProduct(ProductData productData) {
        executeSql("UPDATE PRODUCT SET price=" + productData.getPrice() + " , count=" + productData.getCount() + " WHERE name='" + productData.getName() + "'");
    }

    @Override
    public ProductData getProduct(String name) {
        String sql = "SELECT * FROM PRODUCT WHERE name ='" + name + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                return loadProduct(resultSet);
            } else {
                throw new ProductNotFoundException(name);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private ProductData loadProduct(ResultSet resultSet) throws SQLException {
        return new ProductData(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("count"));
    }

    @Override
    public void saveCart(CartData cartData) {
        try {
            getCartWithoutProducts(cartData.getId());
            updateCart(cartData);
        } catch (CartNotFoundException e) {
            insertCart(cartData);
        }
    }

    private void insertCartProducts(CartData cartData) {
        for (ProductData p : cartData.getProductsData()) {
            insertProductToCart(cartData, p);
        }
    }

    private void cleanProducts(CartData cartData) {
        executeSql("DELETE FROM CART_PRODUCT WHERE cart_id=" + cartData.getId());        
    }

    private void updateCart(CartData cartData) {
        cleanProducts(cartData);
        executeSql("UPDATE CART SET delivery=" + cartData.isHasDelivery() + " WHERE id=" + cartData.getId());
        insertCartProducts(cartData);
    }
    
    private void executeSql(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private CartData getCartWithoutProducts(int cartId) {
        String sql = "SELECT * FROM CART WHERE id ='" + cartId + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                return new CartData(resultSet.getInt("id"), null, resultSet.getBoolean("delivery"));
            } else {
                throw new CartNotFoundException(cartId);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private void insertCart(CartData cartData) {
        executeSql("INSERT INTO CART VALUES ('" + cartData.getId() + "', " + cartData.isHasDelivery() + ")");
        insertCartProducts(cartData);
    }

    private void insertProductToCart(CartData data, ProductData p) {
        executeSql("INSERT INTO CART_PRODUCT VALUES (" + data.getId() + ", '" + p.getName() + "')");
    }

    @Override
    public CartData getCart(int cartId) {
        CartData cart = getCartWithoutProducts(cartId);
        List<ProductData> products = new ArrayList<>();
        String sql = "SELECT * FROM CART_PRODUCT, PRODUCT WHERE cart_id =" + cartId + " AND PRODUCT.name=CART_PRODUCT.product_name";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("here");
                products.add(loadProduct(resultSet));
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return new CartData(cart.getId(), products, cart.isHasDelivery());
    }

    public static void main(String[] args) throws Exception {
        try (H2Persistence p = new H2Persistence()) {
            Product wwine = new Product("wwine", 100, 25);
            p.putProduct(wwine.getData());
            System.out.println(new Product(p.getProduct("wwine")));
            Cart c = new Cart(1);
            c.addDelivery();
            c.addProduct(wwine);
            p.saveCart(c.getData());
            System.out.println(new Cart(p.getCart(1)));
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
