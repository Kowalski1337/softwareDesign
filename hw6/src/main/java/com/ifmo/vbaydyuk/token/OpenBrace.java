package com.ifmo.vbaydyuk.token;

public class OpenBrace extends Brace {
    @Override
    public TokenType getTokenType() {
        return TokenType.LEFT;
    }
}
