package com.ifmo.vbaydyuk.utils;

import com.ifmo.vbaydyuk.Product;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ifmo.vbaydyuk.servlet.GetProductsServlet.GET_PRODUCTS_TITLE;
import static com.ifmo.vbaydyuk.servlet.QueryServlet.COUNT_TITLE;
import static com.ifmo.vbaydyuk.servlet.QueryServlet.MAX_TITLE;
import static com.ifmo.vbaydyuk.servlet.QueryServlet.MIN_TITLE;
import static com.ifmo.vbaydyuk.servlet.QueryServlet.SUM_TITLE;

public class HtmlGenerationUtils {
    private static final String HEADER = "<html><body>";
    private static final String FOOTER = "</body></html>";
    private static final String SEPARATOR = "</br>";
    private static final String TITLE_OPENER = "<h1>";
    private static final String TITLE_CLOSER = "</h1>";
    private static final String LINE_SEPARATOR = "\n";
    private static final String WORD_SEPARATOR = "\t";
    private static final String COLON = ":";

    public static final Pattern GET_PRODUCTS_PATTERN = Pattern.compile(getPatternForMethodWithProducts(GET_PRODUCTS_TITLE));
    public static final Pattern MAX_PATTERN = Pattern.compile(getPatternForMethodWithProducts(MAX_TITLE));
    public static final Pattern MIN_PATTERN = Pattern.compile(getPatternForMethodWithProducts(MIN_TITLE));
    public static final Pattern SUM_PATTERN = Pattern.compile(getPatternForMethodWithNumber(SUM_TITLE));
    public static final Pattern COUNT_PATTERN = Pattern.compile(getPatternForMethodWithNumber(COUNT_TITLE));
    public static final Pattern PRODUCT_PATTERN = Pattern.compile("([^" + WORD_SEPARATOR + "]+)"+ WORD_SEPARATOR + "([0-9]+)" + SEPARATOR + LINE_SEPARATOR);


    public static String generateProductsResponseWithTitle(String title, List<Product> products) {
        return generateResponseWithTitle(
                title,
                products.stream()
                        .map(product -> product.getName() + WORD_SEPARATOR + product.getPrice() + SEPARATOR + LINE_SEPARATOR)
                        .collect(Collectors.joining()),
                true
        );
    }

    public static String generateNumericResponseWithTitle(String title, Long num) {
        return generateResponseWithTitle(title, (num == null ? "" : num + LINE_SEPARATOR), false);
    }

    private static String generateResponseWithTitle(String title, String content, boolean withHeader) {
        return HEADER + LINE_SEPARATOR
                + generateHeader(title, withHeader) + LINE_SEPARATOR +
                content
                + FOOTER;
    }

    private static String generateHeader(String title, boolean withHeader) {
        if (withHeader) {
            return TITLE_OPENER + title + COLON + TITLE_CLOSER;
        } else {
            return title + COLON;
        }
    }

    private static String getPatternForMethodWithProducts(String title) {
        return generateResponseWithTitle(title,
                "(([^" + WORD_SEPARATOR + "]+" + WORD_SEPARATOR + "[0-9]+" + SEPARATOR + LINE_SEPARATOR + ")*)",
                true
        ) + "\r\n";
    }

    private static String getPatternForMethodWithNumber(String title) {
        return generateResponseWithTitle(title,
                "([0-9]+)" + LINE_SEPARATOR,
                false
        ) + "\r\n";
    }
}
