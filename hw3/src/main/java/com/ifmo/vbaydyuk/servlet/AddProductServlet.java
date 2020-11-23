package com.ifmo.vbaydyuk.servlet;

import com.ifmo.vbaydyuk.Product;
import com.ifmo.vbaydyuk.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    public static final String NAME = "name";
    public static final String PRICE = "price";

    public AddProductServlet(ProductsDAO productsDAO) {
        super(productsDAO);
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        String name = request.getParameter(NAME);
        long price = Long.parseLong(request.getParameter(PRICE));
        productsDAO.insertProducts(Collections.singletonList(new Product(name, price)));
        return "OK";
    }
}
