package com.luxoft.training.solid.store.persistence.h2;

import com.luxoft.training.solid.store.Cart;
import com.luxoft.training.solid.store.CartData;
import com.luxoft.training.solid.store.Product;
import com.luxoft.training.solid.store.persistence.Persistence;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

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
    public void putProduct(String name, Product product) {
        if (getProduct(name) == null) {
            insertProduct(product);
        } else {
            updateProduct(product);
        }
    }

    private void insertProduct(Product product) {
        executeSql("INSERT INTO PRODUCT VALUES ('" + product.getName() + "', " + product.getPrice() + " , " + product.getCount() + ")");
    }

    private void updateProduct(Product product) {
        executeSql("UPDATE PRODUCT SET price=" + product.getPrice() + " , count=" + product.getCount() + " WHERE name='" + product.getName() + "'");
    }

    @Override
    public Product getProduct(String name) {
        Product product = null;
        String sql = "SELECT * FROM PRODUCT WHERE name ='" + name + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                product = loadProduct(resultSet);
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return product;
    }

    private Product loadProduct(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("count"));
    }

    @Override
    public void putCart(int cartId, Cart cart) {
        CartData cartData = cart.getData();
        if (getCartWithoutProducts(cartId) == null) {
            insertCart(cartData);
        } else {
            updateCart(cartData);
            cleanProducts(cartData);
        }
        for (Product p : cartData.getProducts()) {
            insertProductToCart(cartData, p);
        }
    }

    private void cleanProducts(CartData cartData) {
        executeSql("DELETE FROM CART_PRODUCT WHERE cart_id=" + cartData.getId());        
    }

    private void updateCart(CartData cartData) {
        executeSql("UPDATE CART SET delivery=" + cartData.isHasDelivery() + " WHERE id=" + cartData.getId());
    }
    
    private void executeSql(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Cart getCartWithoutProducts(int cartId) {
        Cart cart = null;
        String sql = "SELECT * FROM CART WHERE id ='" + cartId + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                cart = new Cart(resultSet.getInt("id"));
                if (resultSet.getBoolean("delivery")) {
                    cart.addDelivery();
                }
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return cart;
    }

    private void insertCart(CartData data) {
        executeSql("INSERT INTO CART VALUES ('" + data.getId() + "', " + data.isHasDelivery() + ")");
    }

    private void insertProductToCart(CartData data, Product p) {
        executeSql("INSERT INTO CART_PRODUCT VALUES (" + data.getId() + ", '" + p.getName() + "')");
    }

    @Override
    public Cart getCart(int cartId) {
        Cart cart = getCartWithoutProducts(cartId);
        String sql = "SELECT * FROM CART_PRODUCT, PRODUCT WHERE cart_id ='" + cartId + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                cart.addProduct(loadProduct(resultSet));
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return cart;
    }

    public static void main(String[] args) throws Exception {
        try (H2Persistence p = new H2Persistence()) {
            Product wwine = new Product("wwine", 100, 25);
            p.putProduct("wwine", wwine);
            System.out.println(p.getProduct("wwine"));
            Cart c = new Cart(1);
            c.addDelivery();
            c.addProduct(wwine);
            p.putCart(1, c);
            System.out.println(p.getCart(1));
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
