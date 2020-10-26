package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    private final ProductsDAO productsDAO;

    public QueryServlet(ProductsDAO productsDAO) {
        super(productsDAO);
        this.productsDAO = productsDAO;
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            return generateItemsResponse(productsDAO.getMaxProducts(), "Items with max price");
        } else if ("min".equals(command)) {
            return generateItemsResponse(productsDAO.getMinProducts(), "Items with min price");
        } else if ("sum".equals(command)) {
            Long sum = productsDAO.getSum();
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>\n");
            sb.append("Summary price: \n");
            if (sum != null) sb.append(sum).append('\n');
            sb.append("</body></html>");
            return sb.toString();
        } else if ("count".equals(command)) {
            Long count = productsDAO.getCount();
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>\n");
            sb.append("Number of products: \n");
            if (count != null) sb.append(count).append('\n');
            sb.append("</body></html>");
            return sb.toString();
        } else {
            return "Unknown command: " + command;
        }
    }

}
