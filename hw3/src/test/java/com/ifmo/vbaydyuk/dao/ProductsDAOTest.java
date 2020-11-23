package com.ifmo.vbaydyuk.dao;

import com.ifmo.vbaydyuk.DAOBasedTests;

import com.ifmo.vbaydyuk.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ifmo.vbaydyuk.TestUtils.generateProducts;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ProductsDAOTest implements DAOBasedTests {
    private static final String PRODUCT = "PRODUCT";
    private static final String JDBC_PATH = "jdbc:sqlite:testDB.db";
    private static final ProductsDAO PRODUCTS_DAO = new ProductsDAO(JDBC_PATH);
    private static final Product TEST_PRODUCT = new Product("Product", 1000);


    @Before
    public void setUp() {
        cleanUpTable(JDBC_PATH, PRODUCT);
    }

    @Test
    public void testAddProduct() {
        PRODUCTS_DAO.insertProducts(Collections.singletonList(TEST_PRODUCT));
        assertThat(PRODUCTS_DAO.getAllProducts(), contains(TEST_PRODUCT));
    }

    @Test
    public void testAddProducts() {
        List<Product> products = generateProducts();
        PRODUCTS_DAO.insertProducts(products);
        assertEquals(products, PRODUCTS_DAO.getAllProducts());
    }

    @Test
    public void testAddProductsOneByOne() {
        List<Product> products = generateProducts();
        products.stream()
                .map(Collections::singletonList)
                .forEach(PRODUCTS_DAO::insertProducts);
        assertEquals(products, PRODUCTS_DAO.getAllProducts());
    }

    @Test
    public void testGetMaxProductsEmpty() {
        assertEquals(emptyList(), PRODUCTS_DAO.getMaxProducts());
    }

    @Test
    public void testGetMaxProductsRandom() {
        List<Product> products = generateProducts();
        PRODUCTS_DAO.insertProducts(products);
        List<Product> max = products
                .stream()
                .collect(Collectors.groupingBy(Product::getPrice))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .orElse(emptyList());
        assertEquals(max, PRODUCTS_DAO.getMaxProducts());
    }

    @Test
    public void testGetMinProductsEmpty() {
        assertEquals(emptyList(), PRODUCTS_DAO.getMinProducts());
    }

    @Test
    public void testGetMinProductsRandom() {
        List<Product> products = generateProducts();
        PRODUCTS_DAO.insertProducts(products);
        List<Product> min = products
                .stream()
                .collect(Collectors.groupingBy(Product::getPrice))
                .entrySet()
                .stream()
                .min(Comparator.comparingLong(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .orElse(emptyList());
        assertEquals(min, PRODUCTS_DAO.getMinProducts());
    }

    @Test
    public void testGetSumEmpty() {
        assertEquals(new Long(0), PRODUCTS_DAO.getSum());
    }

    @Test
    public void testGetSumRandom() {
        List<Product> products = generateProducts();
        PRODUCTS_DAO.insertProducts(products);
        Long sum = products
                .stream()
                .map(Product::getPrice)
                .reduce(0L, Long::sum);
        assertEquals(sum, PRODUCTS_DAO.getSum());
    }

    @Test
    public void testGetCountEmpty() {
        assertEquals(new Long(0), PRODUCTS_DAO.getCount());
    }

    @Test
    public void testGetCountRandom() {
        List<Product> products = generateProducts();
        PRODUCTS_DAO.insertProducts(products);
        Long count = (long) products.size();
        assertEquals(count, PRODUCTS_DAO.getCount());
    }
}