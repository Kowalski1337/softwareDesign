package com.ifmo.vbaydyuk.token;

import com.ifmo.vbaydyuk.exception.EvaluateException;
import com.ifmo.vbaydyuk.visitor.TokenVisitor;

public abstract class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public abstract int eval(int left, int right) throws EvaluateException;

    public abstract int getPriority();

    @Override
    public String toString() {
        return getTokenType().toString();
    }
}
