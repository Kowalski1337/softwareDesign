package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            if (sum != null) response.getWriter().println(sum);
            response.getWriter().println("</body></html>");
            ServletCommon.doGoodies(response);
        } else if ("count".equals(command)) {
            Long count = productsDAO.getCount();
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            if (count != null) response.getWriter().println(count);
            response.getWriter().println("</body></html>");
            ServletCommon.doGoodies(response);
        } else {
            response.getWriter().println("Unknown command: " + command);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}
