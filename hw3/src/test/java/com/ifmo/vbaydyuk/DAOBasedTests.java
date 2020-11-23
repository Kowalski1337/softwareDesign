package com.ifmo.vbaydyuk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DAOBasedTests {
    String DELETE_FROM = "DELETE FROM";

    default void cleanUpTable(String path, String tableName) {
        try (Connection connection = DriverManager.getConnection(path)) {
            connection.createStatement().executeUpdate(DELETE_FROM + " " + tableName);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot clean up table PRODUCTS");
        }
    }
}
