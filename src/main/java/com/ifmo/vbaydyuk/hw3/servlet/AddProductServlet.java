package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.Product;
import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(ProductsDAO productsDAO) {
        super(productsDAO);
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        productsDAO.insertProducts(Collections.singletonList(new Product(name, price)));
        return "OK";
    }
}
