package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    public static final String TITLE = "All items that we have";

    public GetProductsServlet(ProductsDAO productsDAO) {
        super(productsDAO);
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        return generateItemsResponse(productsDAO.getAllProducts(), TITLE);
    }
}
