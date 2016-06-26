package com.luxoft.training.solid.store.persistence.h2;

import com.luxoft.training.solid.store.provisioning.PersistenceException;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class H2Connection implements Closeable {

    private final Connection connection;

    public H2Connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public void executeSql(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            return connection.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
