package com.ifmo.vbaydyuk.hw3.servlet;

import com.ifmo.vbaydyuk.hw3.dao.ProductsDAO;

import javax.servlet.http.HttpServletRequest;

import static com.ifmo.vbaydyuk.hw3.utils.HtmlGenerationUtils.generateNumericResponseWithTitle;
import static com.ifmo.vbaydyuk.hw3.utils.HtmlGenerationUtils.generateProductsResponseWithTitle;

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
    public static final String SUM_TITLE = "Summary price";
    public static final String COUNT_TITLE = "Number of products";
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
                return generateProductsResponseWithTitle(MAX_TITLE, productsDAO.getMaxProducts());
            case MIN:
                return generateProductsResponseWithTitle(MIN_TITLE, productsDAO.getMinProducts());
            case SUM:
                return generateNumericResponseWithTitle(SUM_TITLE, productsDAO.getSum());
            case COUNT:
                return generateNumericResponseWithTitle(COUNT_TITLE, productsDAO.getCount());
            default:
                return "Unknown command: " + command;
        }
    }

}
