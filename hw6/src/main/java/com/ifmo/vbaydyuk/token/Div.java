package com.ifmo.vbaydyuk.token;

import com.ifmo.vbaydyuk.exception.EvaluateException;

public class Div extends Operation {
    @Override
    public int eval(int left, int right) throws EvaluateException {
        if (right == 0)
            throw new EvaluateException("Division by zero is not allowed");
        return left / right;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.DIV;
    }
}
