package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Tokenizer;
import com.ifmo.vbaydyuk.visitor.TokenVisitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class VisitorsComponent implements CommandLineRunner {
    private final List<TokenVisitor> visitors;
    private final Tokenizer tokenizer;

    public VisitorsComponent(List<TokenVisitor> visitors, Tokenizer tokenizer) {
        this.visitors = visitors;
        this.tokenizer = tokenizer;
    }

    @Override
    public void run(String... args) {
        TokensResponseWithError tokensResponseWithError = tokenizer.getResult();
        for (TokenVisitor visitor : visitors) {
            tokensResponseWithError = visitor.apply(tokensResponseWithError);;
        }
        if (!tokensResponseWithError.isSuccess()) {
            System.out.println(tokensResponseWithError.getErrorMessage());
        }
    }
}
