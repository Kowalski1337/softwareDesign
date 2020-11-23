package com.ifmo.vbaydyuk.servlet;

import com.ifmo.vbaydyuk.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
    public static final String CONTENT_TYPE = "text/html";
    protected final ProductsDAO productsDAO;

    protected AbstractServlet(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(getResponseContent(request));
    }

    protected abstract String getResponseContent(HttpServletRequest request);
}
