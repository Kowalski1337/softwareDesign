package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.Product;
import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class AbstractServlet extends HttpServlet {
    protected final ProductsDAO productsDAO;

    protected AbstractServlet(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(getResponseContent(request));
    }

    protected abstract String getResponseContent(HttpServletRequest request);

    protected static String generateItemsResponse(List<Product> products, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");
        sb.append("<h1>")
                .append(title)
                .append(": </h1>\n");
        for (Product product : products) {
            String name = product.getName();
            long price = product.getPrice();
            sb.append(name)
                    .append("\t")
                    .append(price)
                    .append("</br>\n");
        }
        sb.append("</body></html>");
        return sb.toString();
    }
}
