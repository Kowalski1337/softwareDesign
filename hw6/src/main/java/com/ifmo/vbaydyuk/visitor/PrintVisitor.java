package com.ifmo.vbaydyuk.visitor;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Brace;
import com.ifmo.vbaydyuk.token.NumberToken;
import com.ifmo.vbaydyuk.token.Operation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PrintVisitor implements TokenVisitor {
    private StringBuilder builder;

    @Override
    public void visit(NumberToken token) {
        builder.append(token).append(' ');
    }

    @Override
    public void visit(Brace token) {
        builder.append(token).append(' ');
    }

    @Override
    public void visit(Operation token) {
        builder.append(token).append(' ');
    }

    @Override
    public TokensResponseWithError applyOnSuccess(TokensResponseWithError tokensResponseWithError) {
        builder = new StringBuilder();
        tokensResponseWithError.getTokens().forEach(token -> token.accept(this));
        System.out.println(builder.toString());
        return tokensResponseWithError;
    }
}
