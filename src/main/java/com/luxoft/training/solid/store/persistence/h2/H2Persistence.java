package com.luxoft.training.solid.store.persistence.h2;

import com.luxoft.training.solid.store.Cart;
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

        String createCartTableSql = "CREATE TABLE IF NOT EXISTS PRODUCT"
                + "  (name           VARCHAR(30),"
                + "   price            FLOAT,"
                + "   count          INTEGER,"
                + " PRIMARY KEY (name)"
                + ")";

        connection.createStatement().execute(createProductSql);        
    }
    
    @Override
    public void putProduct(String name, Product product) {
        String sql = "INSERT INTO PRODUCT VALUES ('" + product.getName() + "', " + product.getPrice() + " , " + product.getCount() + ")";
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Product getProduct(String name) {
        String sql = "SELECT * FROM PRODUCT WHERE name ='" + name + "'";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                return new Product(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("count"));
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return null;
    }

    @Override
    public void putCart(int cartId, Cart cart) {

    }

    @Override
    public Cart getCart(int cartId) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        try (H2Persistence p = new H2Persistence()) {
            p.putProduct("wwine", new Product("wwine", 100, 5));
            System.out.println(p.getProduct("wwine"));
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
