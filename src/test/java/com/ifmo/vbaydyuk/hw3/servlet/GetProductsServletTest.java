package com.ifmo.vbaydyuk.hw3.servlet;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.ifmo.vbaydyuk.hw3.Product;
import com.ifmo.vbaydyuk.hw3.utils.HtmlGenerationUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.ifmo.vbaydyuk.hw3.TestUtils.generateProducts;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GetProductsServletTest extends ServletTestBase {
    private static final List<Product> TEST_PRODUCTS = ImmutableList.of(
            new Product("Product1", 1000),
            new Product("Product2", 500)
    );

    @Test
    public void testGetProductsEmptyData() {
        assertThat(getProducts(), is(empty()));
    }

    @Test
    public void testGetProducts() {
        insertProducts(TEST_PRODUCTS);
        assertEquals(TEST_PRODUCTS, getProducts());
    }

    @Test
    public void testGetProductsRandomData() {
        List<Product> testProducts = generateProducts();
        insertProducts(testProducts);
        assertEquals(testProducts, getProducts());
    }

    private static List<Product> getProducts() {
        return getProducts(getProductsResponse(), HtmlGenerationUtils.GET_PRODUCTS_PATTERN);
    }

    private static String getProductsResponse() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + GET_PRODUCTS);
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
    }
}
