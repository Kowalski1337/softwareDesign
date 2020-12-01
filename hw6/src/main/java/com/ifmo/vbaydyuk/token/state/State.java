package com.ifmo.vbaydyuk.token.state;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Token;
import com.ifmo.vbaydyuk.token.Tokenizer;

import java.util.List;

public abstract class State {
    protected final Tokenizer tokenizer;

    public State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void onEnd() {
        tokenizer.changeState(new EndState(tokenizer));
    }

    public void onUnexpected() {
        tokenizer.changeState(new ErrorState(tokenizer, "unexpected symbol", tokenizer.getPosition()));
        tokenizer.next();
    }

    public TokensResponseWithError getResult() {
        return TokensResponseWithError.error("Unexpected call");
    }

    public abstract void onNumber();

    public abstract void onBrace();

    public abstract void onOperation();
}
