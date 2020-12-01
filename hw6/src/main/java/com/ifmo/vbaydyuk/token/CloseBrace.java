package com.ifmo.vbaydyuk.token;

public class CloseBrace extends Brace {
    @Override
    public TokenType getTokenType() {
        return TokenType.RIGHT;
    }
}
