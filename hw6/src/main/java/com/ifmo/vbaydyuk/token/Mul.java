package com.ifmo.vbaydyuk.token;

public class Mul extends Operation {
    @Override
    public int eval(int left, int right) {
        return left * right;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.MUL;
    }
}
