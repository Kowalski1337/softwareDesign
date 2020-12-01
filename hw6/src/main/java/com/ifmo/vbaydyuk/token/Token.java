package com.ifmo.vbaydyuk.token;

import com.ifmo.vbaydyuk.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
    TokenType getTokenType();
    default int getPriority() {
        return 0;
    }
}