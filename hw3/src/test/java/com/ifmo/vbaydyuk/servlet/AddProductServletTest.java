package com.ifmo.vbaydyuk.servlet;

import com.ifmo.vbaydyuk.Product;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.ifmo.vbaydyuk.TestUtils.generateProducts;
import static com.ifmo.vbaydyuk.servlet.AddProductServlet.NAME;
import static com.ifmo.vbaydyuk.servlet.AddProductServlet.PRICE;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddProductServletTest extends ServletTestBase {
    private static final Product TEST_PRODUCT = new Product("product", 1000);
    public static final String OK = "OK\r\n";

    @Test
    public void testAddProduct() {
        addProduct(TEST_PRODUCT);
        assertThat(getProductsDB(), contains(TEST_PRODUCT));
    }

    @Test
    public void testAddRandomProducts() {
        List<Product> products = generateProducts();
        products.forEach(AddProductServletTest::addProduct);
        assertEquals(products, getProductsDB());
    }


    private static void addProduct(Product product) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(SERVER_PATH + SERVER_PORT + ADD_PRODUCT)
                .queryParam(NAME, product.getName())
                .queryParam(PRICE, product.getPrice());
        String response = restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
        assertEquals(OK, response);
    }
}