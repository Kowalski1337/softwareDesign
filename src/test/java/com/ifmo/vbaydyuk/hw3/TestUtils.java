package com.ifmo.vbaydyuk.hw3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestUtils {
    public static List<Product> generateProducts() {
        Random random = new Random();
        int count = 100 + random.nextInt(100);
        String product = "Product";
        AtomicInteger i = new AtomicInteger();
        return Stream.generate(i::getAndIncrement)
                .limit(count)
                .map(number -> new Product(product + number, random.nextInt(1000)))
                .collect(Collectors.toList());
    }
}
