package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;

import static com.ifmo.vbaydyuk.hw3.utils.HtmlGenerationUtils.generateProductsResponseWithTitle;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    public static final String GET_PRODUCTS_TITLE = "All items that we have";

    public GetProductsServlet(ProductsDAO productsDAO) {
        super(productsDAO);
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        return generateProductsResponseWithTitle(GET_PRODUCTS_TITLE, productsDAO.getAllProducts());
    }
}
