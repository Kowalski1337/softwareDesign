package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.DAOBasedTests;
import com.ifmo.vbaydyuk.hw3.Product;
import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author vbaydyuk
 * @since 26.10.2020
 */
public class ServletTestBase implements DAOBasedTests {
    private static final String PRODUCT = "PRODUCT";
    private static final String JDBC_PATH = "jdbc:sqlite:test.db";
    protected static final String SERVER_PATH = "http://localhost:";
    protected static final int SERVER_PORT = 8081;
    protected static final String ADD_PRODUCT = "/add-product";
    protected static final String GET_PRODUCTS = "/get-products";
    protected static final String QUERY = "/query";

    protected static final Pattern PRODUCT_PATTERN = Pattern.compile("([^\t]+)\t([0-9]+)</br>\r\n");

    private final Server server;
    private final ProductsDAO productsDAO;

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
        cleanUpTable(JDBC_PATH, PRODUCT);
    }

    public ServletTestBase() {
        productsDAO = new ProductsDAO(JDBC_PATH);
        server = createServer();
    }

    private Server createServer() {
        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productsDAO)), ADD_PRODUCT);
        context.addServlet(new ServletHolder(new GetProductsServlet(productsDAO)), GET_PRODUCTS);
        context.addServlet(new ServletHolder(new QueryServlet(productsDAO)), QUERY);
        return server;
    }

    protected void insertProducts(List<Product> products) {
        productsDAO.insertProducts(products);
    }

    protected List<Product> getProductsDB() {
        return productsDAO.getAllProducts();
    }

    protected static List<Product> getProducts(String response, Pattern pattern) {
        Matcher getProductMatcher = pattern.matcher(response);
        assertTrue(getProductMatcher.matches());
        String productsGroup = getProductMatcher.group(1);
        Matcher productMatcher = PRODUCT_PATTERN.matcher(productsGroup);
        List<Product> products = new ArrayList<>();
        while (productMatcher.find()) {
            String name = productMatcher.group(1);
            String price = productMatcher.group(2);
            assertTrue(isNumber(price));
            products.add(new Product(name, Integer.parseInt(price)));
        }
        return products;
    }

}
