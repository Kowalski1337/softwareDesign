package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductsDAO productsDAO;

    public QueryServlet(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String command = request.getParameter("command");

        if ("max".equals(command)) {
            ServletCommon.dumpItems(response, productsDAO.getMaxProducts(), "Items with max price");
            ServletCommon.doGoodies(response);
        } else if ("min".equals(command)) {
            ServletCommon.dumpItems(response, productsDAO.getMinProducts(), "Items with min price");
            ServletCommon.doGoodies(response);
        } else if ("sum".equals(command)) {
            Long sum = productsDAO.getSum();
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("Summary price: ");
            if (sum != null) writer.println(sum);
            writer.println("</body></html>");
            ServletCommon.doGoodies(response);
        } else if ("count".equals(command)) {
            Long count = productsDAO.getCount();
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("Number of products: ");
            if (count != null) writer.println(count);
            writer.println("</body></html>");
            ServletCommon.doGoodies(response);
        } else {
            response.getWriter().println("Unknown command: " + command);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}
