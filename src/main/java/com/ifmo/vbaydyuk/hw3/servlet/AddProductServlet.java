package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.Product;
import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final ProductsDAO productsDAO;

    public AddProductServlet(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        productsDAO.insertProducts(Collections.singletonList(new Product(name, price)));
        ServletCommon.doGoodies(response);
        response.getWriter().println("OK");
    }
}
