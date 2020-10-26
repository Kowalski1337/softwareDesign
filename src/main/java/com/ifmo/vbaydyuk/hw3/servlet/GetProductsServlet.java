package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductsDAO productsDAO;

    public GetProductsServlet(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletCommon.dumpItems(response, productsDAO.getAllProducts(), "All items that we have");
        ServletCommon.doGoodies(response);
    }
}
