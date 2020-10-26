package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    public static final String MAX_TITLE = "Items with max price";
    public static final String MIN_TITLE = "Items with min price";
    public static final String COMMAND = "command";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String SUM = "sum";
    public static final String COUNT = "count";
    private final ProductsDAO productsDAO;

    public QueryServlet(ProductsDAO productsDAO) {
        super(productsDAO);
        this.productsDAO = productsDAO;
    }

    @Override
    protected String getResponseContent(HttpServletRequest request) {
        String command = request.getParameter(COMMAND);
        switch (command) {
            case MAX:
                return generateItemsResponse(productsDAO.getMaxProducts(), MAX_TITLE);
            case MIN:
                return generateItemsResponse(productsDAO.getMinProducts(), MIN_TITLE);
            case SUM: {
                Long sum = productsDAO.getSum();
                StringBuilder sb = new StringBuilder();
                sb.append("<html><body>\n");
                sb.append("Summary price: \n");
                if (sum != null) sb.append(sum).append('\n');
                sb.append("</body></html>");
                return sb.toString();
            }
            case COUNT: {
                Long count = productsDAO.getCount();
                StringBuilder sb = new StringBuilder();
                sb.append("<html><body>\n");
                sb.append("Number of products: \n");
                if (count != null) sb.append(count).append('\n');
                sb.append("</body></html>");
                return sb.toString();
            }
            default:
                return "Unknown command: " + command;
        }
    }

}
