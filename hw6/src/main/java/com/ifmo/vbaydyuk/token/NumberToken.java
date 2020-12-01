package com.ifmo.vbaydyuk.token;

import com.ifmo.vbaydyuk.visitor.TokenVisitor;

public class NumberToken implements Token {
    private final int num;

    public NumberToken(int num) {
        this.num = num;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }

    @Override
    public String toString() {
        return getTokenType().toString() + "(" + getNum() + ")";
    }

    public int getNum() {
        return num;
    }
}
