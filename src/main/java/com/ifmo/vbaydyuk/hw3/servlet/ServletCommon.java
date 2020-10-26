package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author volhovm
 */
public class ServletCommon {
    public static void dumpItems(HttpServletResponse response, List<Product> products, String title) {
        try {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>" + title + ": </h1>");
            for (Product product : products) {
                String name = product.getName();
                long price = product.getPrice();
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write response", e);
        }
    }

    public static void doGoodies(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
