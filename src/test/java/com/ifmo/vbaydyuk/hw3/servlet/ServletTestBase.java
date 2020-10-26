package com.ifmo.vbaydyuk.hw3.servlet;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author vbaydyuk
 * @since 26.10.2020
 */
public class ServletTestBase {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
    private static final String DELETE_FROM_TABLE = "DELETE FROM PRODUCT";
    private static final String INSERT = "INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES";
    private static final String SELECT = "SELECT * FROM PRODUCT";
    private static final String JDBC_PATH = "jdbc:sqlite:test.db";
    protected static final String SERVER_PATH = "http://localhost:";
    protected static final int SERVER_PORT = 8081;
    protected static final String ADD_PRODUCT = "/add-product";
    protected static final String GET_PRODUCTS = "/get-products";
    protected static final String QUERY = "/query";
    protected static final Map<String, Integer> TEST_PRODUCTS = ImmutableMap.of(
            "Product1", 1000,
            "Product2", 500
    );

    private final Server server;
    private final Connection connection;

    @Before
    public void setUp() {
        Thread thread = new Thread(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                throw new AssertionError("Cannot start server", e);
            }
        });
        thread.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        executeSql(DELETE_FROM_TABLE);
        connection.close();
    }

    public ServletTestBase() {
        try {
            connection = DriverManager.getConnection(JDBC_PATH);
            executeSql(CREATE_TABLE);
            server = createServer();
        } catch (SQLException e) {
            throw new AssertionError("Cannot create server", e);
        }
    }

    private Server createServer() {
        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), ADD_PRODUCT);
        context.addServlet(new ServletHolder(new GetProductsServlet()), GET_PRODUCTS);
        context.addServlet(new ServletHolder(new QueryServlet()), QUERY);
        return server;
    }

    protected void insertProducts(Map<String, Integer> products) throws SQLException {
        String values = products.entrySet()
                .stream()
                .map(e -> "(\"" + e.getKey() + "\"," + e.getValue() + ")")
                .collect(Collectors.joining(","));
        executeSql(INSERT + values);
    }

    protected Map<String, Integer> getProductsDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(SELECT);
        Map<String, Integer> ans = new HashMap<>();
        while (result.next()) {
            ans.put(result.getString("name"), result.getInt("price"));
        }
        return ans;
    }

    private void executeSql(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    protected static Map<String, Integer> generateProducts() {
        Random random = new Random();
        int count = 100 + random.nextInt(100);
        String product = "Product";
        AtomicInteger i = new AtomicInteger();
        return Stream.generate(i::getAndIncrement)
                .limit(count)
                .map(number -> product + number)
                .collect(Collectors.toMap(Function.identity(), p -> random.nextInt(1000)));
    }

}
