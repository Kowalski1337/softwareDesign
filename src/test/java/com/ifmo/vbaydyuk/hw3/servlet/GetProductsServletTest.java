package com.ifmo.vbaydyuk.hw3.servlet;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GetProductsServletTest extends ServletTestBase {
    private static final Pattern GET_PRODUCTS_PATTERN = Pattern.compile("<html><body>\r\n" +
            "<h1>All items that we have: </h1>\r\n" +
            "(([^\t]+\t[0-9]+</br>\r\n)*)" +
            "</body></html>\r\n");
    private static final Map<String, Integer> TEST_PRODUCTS = ImmutableMap.of(
            "Product1", 1000,
            "Product2", 500
    );

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

    private static Map<String, Integer> getProducts() {
        return getProducts(getProductsResponse(), GET_PRODUCTS_PATTERN);
    }


    private static String getProductsResponse() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + GET_PRODUCTS);
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
    }
}