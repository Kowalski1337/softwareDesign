package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.token.Tokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@Order(1)
public class TokenizerComponent implements CommandLineRunner {
    private final Tokenizer tokenizer;

    @Value("classpath:expression.txt")
    private Resource expressionPath;

    public TokenizerComponent(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(expressionPath.getURL().openStream())) {
            tokenizer.parse(scanner.nextLine());
        } catch (IOException e) {
            System.out.println("Source is absent");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
