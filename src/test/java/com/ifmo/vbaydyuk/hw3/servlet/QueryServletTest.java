package com.ifmo.vbaydyuk.hw3.servlet;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.ifmo.vbaydyuk.hw3.Product;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryServletTest extends ServletTestBase {

    private static final Pattern MAX_PATTERN = Pattern.compile("<html><body>\n" +
            "<h1>Items with max price: </h1>\n" +
            "(([^\t]+\t[0-9]+</br>\n)*)" +
            "</body></html>\r\n");

    private static final Pattern MIN_PATTERN = Pattern.compile("<html><body>\n" +
            "<h1>Items with min price: </h1>\n" +
            "(([^\t]+\t[0-9]+</br>\n)*)" +
            "</body></html>\r\n");

    private static final Pattern SUM_PATTERN = Pattern.compile("<html><body>\n" +
            "Summary price: \n" +
            "([0-9]+)\n" +
            "</body></html>\r\n");

    private static final Pattern COUNT_PATTERN = Pattern.compile("<html><body>\n" +
            "Number of products: \n" +
            "([0-9]+)\n" +
            "</body></html>\r\n");

    private static final int MAX_PRICE = 1000;
    private static final int MIN_PRICE = 500;
    private static final int SOME_PRICE = 800;
    private static final List<Product> TEST_PRODUCTS = ImmutableList.of(
            new Product("Product1", MIN_PRICE),
            new Product("Product2", MIN_PRICE),
            new Product("Product3", MAX_PRICE),
            new Product("Product5", MAX_PRICE),
            new Product("Product4", SOME_PRICE)
    );

    private static final String COMMAND = "command";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String SUM = "sum";

    @Test
    public void testMax() {
        List<Product> max = TEST_PRODUCTS
                .stream()
                .filter(e -> e.getPrice() == MAX_PRICE)
                .collect(Collectors.toList());
        insertProducts(TEST_PRODUCTS);
        assertEquals(max, getProducts(getQueryResponse(MAX), MAX_PATTERN));
    }

    @Test
    public void testMin() {
        List<Product> min = TEST_PRODUCTS
                .stream()
                .filter(e -> e.getPrice() == MIN_PRICE)
                .collect(Collectors.toList());
        insertProducts(TEST_PRODUCTS);
        assertEquals(min, getProducts(getQueryResponse(MIN), MIN_PATTERN));
    }

    @Test
    public void testSum() {
        long sum = TEST_PRODUCTS
                .stream()
                .map(Product::getPrice)
                .reduce(0L, Long::sum);
        insertProducts(TEST_PRODUCTS);
        assertEquals(sum, getAggregatedValue(getQueryResponse(SUM), SUM_PATTERN));
    }

    @Test
    public void testCount() {
        long count = TEST_PRODUCTS.size();
        insertProducts(TEST_PRODUCTS);
        assertEquals(count, getAggregatedValue(getQueryResponse("count"), COUNT_PATTERN));
    }

    private static int getAggregatedValue(String response, Pattern pattern) {
        Matcher matcher = pattern.matcher(response);
        assertTrue(matcher.matches());
        String value = matcher.group(1);
        assertTrue(isNumber(value));
        return Integer.parseInt(value);
    }

    private static String getQueryResponse(String command) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + QUERY)
                .queryParam(COMMAND, command);
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
    }
}