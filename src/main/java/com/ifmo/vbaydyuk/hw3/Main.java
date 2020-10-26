package com.ifmo.vbaydyuk.hw3;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;
import com.ifmo.vbaydyuk.hw3.servlet.AddProductServlet;
import com.ifmo.vbaydyuk.hw3.servlet.GetProductsServlet;
import com.ifmo.vbaydyuk.hw3.servlet.QueryServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author akirakozov
 */
public class Main {
    private static final String JDBC_PATH = "jdbc:sqlite:app.db";

    public static void main(String[] args) throws Exception {
        ProductsDAO productsDAO = new ProductsDAO(JDBC_PATH);

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productsDAO)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productsDAO)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productsDAO)), "/query");

        server.start();
        server.join();
    }
}
