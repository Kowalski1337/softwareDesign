package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author volhovm
 */
public class ServletCommon {
    public static void dumpItems(HttpServletResponse response, List<Product> products, String title) {
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>" + title + ": </h1>");
            for (Product product : products) {
                String name = product.getName();
                long price = product.getPrice();
                writer.println(name + "\t" + price + "</br>");
            }
            writer.println("</body></html>");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write response", e);
        }
    }

    public static void doGoodies(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
