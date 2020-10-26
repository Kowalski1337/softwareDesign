package com.ifmo.vbaydyuk.hw3;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetProductsServletTest extends ServletTestBase {
    private static final Pattern GET_PRODUCTS_PATTERN = Pattern.compile("<html><body>\r\n" +
            "<h1>All items that we have: </h1>\r\n" +
            "(([^\t]+\t[0-9]+</br>\r\n)*)" +
            "</body></html>\r\n");
    private static final String PRODUCT_MATCHER = "([^\t]+)\t([0-9]+)</br>\r\n";
    private static final Pattern PRODUCT_PATTERN = Pattern.compile(PRODUCT_MATCHER);


    @Test
    public void testGetProductsEmptyData() {
        assertThat(getProducts(), anEmptyMap());
    }

    @Test
    public void testGetProducts() throws SQLException {
        insertProducts(TEST_PRODUCTS);
        assertEquals(TEST_PRODUCTS, getProducts());
    }

    @Test
    public void testGetProductsRandomData() throws SQLException {
        Map<String, Integer> testProducts = generateProducts();
        insertProducts(testProducts);
        assertEquals(testProducts, getProducts());
    }

    private static Map<String, Integer> generateProducts() {
        Random random = new Random();
        int count = 100 + random.nextInt(100);
        String product = "Product";
        AtomicInteger i = new AtomicInteger();
        return Stream.generate(i::getAndIncrement)
                .limit(count)
                .map(number -> product + number)
                .collect(Collectors.toMap(Function.identity(), p -> random.nextInt(1000)));
    }

    private static Map<String, Integer> getProducts() {
        String response = getProductsResponse();
        Matcher getProductMatcher = GET_PRODUCTS_PATTERN.matcher(response);
        assertTrue(getProductMatcher.matches());
        String productsGroup = getProductMatcher.group(1);
        Matcher productMatcher = PRODUCT_PATTERN.matcher(productsGroup);
        Map<String, Integer> products = new HashMap<>();
        while (productMatcher.find()) {
            String name = productMatcher.group(1);
            String price = productMatcher.group(2);
            assertTrue(isNumber(price));
            products.put(name, Integer.parseInt(price));
        }
        return products;
    }

    protected static String getProductsResponse() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + GET_PRODUCTS);
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
    }
}
