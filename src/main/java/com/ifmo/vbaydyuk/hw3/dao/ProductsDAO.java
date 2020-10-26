package com.ifmo.vbaydyuk.hw3.dao;

import com.ifmo.vbaydyuk.hw3.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsDAO {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    private static final String INSERT = "INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES";
    private static final String GET_ALL = "SELECT * FROM PRODUCT";
    private static final String GET_MAX = "SELECT * FROM PRODUCT " +
            "WHERE PRICE IN (SELECT MAX(PRICE) FROM PRODUCT)";
    private static final String GET_MIN = "SELECT * FROM PRODUCT " +
            "WHERE PRICE IN (SELECT MIN(PRICE) FROM PRODUCT)";
    private static final String GET_SUM = "SELECT SUM(price) FROM PRODUCT";
    private static final String GET_COUNT = "SELECT COUNT(*) FROM PRODUCT";

    private final String name;

    public ProductsDAO(String name) {
        this.name = name;
        initDB();
    }

    public List<Product> getAllProducts() {
        return getProducts(GET_ALL);
    }

    public List<Product> getMaxProducts() {
        return getProducts(GET_MAX);
    }

    public List<Product> getMinProducts() {
        return getProducts(GET_MIN);
    }

    public Long getSum() {
        return getAggregatedValue(GET_SUM);
    }

    public Long getCount() {
        return getAggregatedValue(GET_COUNT);
    }

    public void insertProducts(List<Product> products) {
        String values = products
                .stream()
                .map(e -> "(\"" + e.getName() + "\"," + e.getPrice() + ")")
                .collect(Collectors.joining(","));
        executeSql(INSERT + values);
    }

    private Long getAggregatedValue(String query) {
        try (Connection connection = DriverManager.getConnection(name)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                return result.getLong(1);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot query products", e);
        }
    }

    private List<Product> getProducts(String query) {
        try (Connection connection = DriverManager.getConnection(name)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            List<Product> ans = new ArrayList<>();
            while (result.next()) {
                ans.add(new Product(result.getString("name"), result.getLong("price")));
            }
            return ans;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot query products", e);
        }
    }

    private void initDB() {
        executeSql(CREATE_TABLE);
    }

    private void executeSql(String sql) {
        try (Connection connection = DriverManager.getConnection(name)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot execute " + sql + " query", e);
        }
    }
}
