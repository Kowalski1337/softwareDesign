package com.ifmo.vbaydyuk.token;

import com.ifmo.vbaydyuk.visitor.TokenVisitor;

public abstract class Brace implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getTokenType().toString();
    }
}
