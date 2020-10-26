package com.ifmo.vbaydyuk.hw3.servlet;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProductServletTest extends ServletTestBase {
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String TEST_PRODUCT = "product";
    private static final int TEST_PRICE = 1000;
    public static final String OK = "OK\r\n";

    @Test
    public void testAddProduct() throws SQLException {
        addProduct(TEST_PRODUCT, TEST_PRICE);
        assertEquals(ImmutableMap.of(TEST_PRODUCT, TEST_PRICE), getProductsDB());
    }

    @Test
    public void testAddRandomProducts() throws SQLException {
        Map<String, Integer> products = generateProducts();
        products.forEach(AddProductServletTest::addProduct);
        assertEquals(products, getProductsDB());
    }


    private static void addProduct(String name, int price) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + ADD_PRODUCT)
                .queryParam(NAME, name)
                .queryParam(PRICE, price);
        String response = restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
        assertEquals(OK, response);
    }
}