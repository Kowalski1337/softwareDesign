package com.ifmo.vbaydyuk.visitor;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Brace;
import com.ifmo.vbaydyuk.token.NumberToken;
import com.ifmo.vbaydyuk.token.Operation;
import com.ifmo.vbaydyuk.token.Token;
import com.ifmo.vbaydyuk.token.TokenType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
@Order(1)
public class ParseVisitor implements TokenVisitor {

    private Stack<Token> stack;
    private List<Token> tokens;

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.getTokenType() == TokenType.LEFT) {
            stack.push(token);
        } else {
            Token cur = stack.pop();
            while (cur.getTokenType() != TokenType.LEFT) {
                tokens.add(cur);
                cur = stack.pop();
            }
        }
    }

    @Override
    public void visit(Operation token) {
        if (!stack.isEmpty()) {
            Token cur = stack.peek();
            while (!stack.isEmpty() && token.getPriority() <= cur.getPriority()) {
                tokens.add(stack.pop());
                if (!stack.isEmpty()) {
                    cur = stack.peek();
                }
            }
        }
        stack.push(token);
    }

    @Override
    public TokensResponseWithError applyOnSuccess(TokensResponseWithError tokensResponseWithError) {
        stack = new Stack<>();
        tokens = new ArrayList<>();
        tokensResponseWithError.getTokens().forEach(token -> token.accept(this));
        while (!stack.isEmpty()) {
            Token token = stack.pop();
            tokens.add(token);
        }
        return TokensResponseWithError.success(tokens);
    }

}
