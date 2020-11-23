package com.ifmo.vbaydyuk.servlet;

import com.ifmo.vbaydyuk.utils.HtmlGenerationUtils;
import com.ifmo.vbaydyuk.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;


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
        return HtmlGenerationUtils.generateProductsResponseWithTitle(GET_PRODUCTS_TITLE, productsDAO.getAllProducts());
    }
}
