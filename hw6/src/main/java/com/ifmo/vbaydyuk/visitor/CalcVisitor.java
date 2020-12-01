package com.ifmo.vbaydyuk.visitor;

import com.ifmo.vbaydyuk.exception.EvaluateException;
import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Brace;
import com.ifmo.vbaydyuk.token.NumberToken;
import com.ifmo.vbaydyuk.token.Operation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
@Order(3)
public class CalcVisitor implements TokenVisitor {

    private Stack<Integer> stack;
    private TokensResponseWithError tokensResponseWithError;

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getNum());
    }

    @Override
    public void visit(Brace token) {
        tokensResponseWithError = TokensResponseWithError.error("Unexpected brace in calc visitor");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            tokensResponseWithError = TokensResponseWithError.error("Wrong expression");
        } else {
            int right = stack.pop();
            int left = stack.pop();
            try {
                stack.push(token.eval(left, right));
            } catch (EvaluateException e) {
                tokensResponseWithError = TokensResponseWithError.error(e.getMessage());
            }
        }
    }

    @Override
    public TokensResponseWithError applyOnSuccess(TokensResponseWithError tokensResponseWithError) {
        stack = new Stack<>();
        this.tokensResponseWithError = null;
        tokensResponseWithError.getTokens().forEach(token -> token.accept(this));
        if (this.tokensResponseWithError == null) {
            if (stack.isEmpty()) {
                return TokensResponseWithError.error("Wrong expression");
            }
            int ans = stack.pop();
            if (!stack.isEmpty()) {
                return TokensResponseWithError.error("Wrong expression");
            }
            System.out.println(ans);
            return tokensResponseWithError;
        }
        return this.tokensResponseWithError;
    }
}
