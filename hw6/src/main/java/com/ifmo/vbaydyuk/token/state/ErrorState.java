package com.ifmo.vbaydyuk.token.state;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Tokenizer;

public class ErrorState extends State {

    private final String cause;
    private final int position;

    public ErrorState(Tokenizer tokenizer, String cause, int position) {
        super(tokenizer);
        this.cause = cause;
        this.position = position;
    }

    @Override
    public void onNumber() {
        tokenizer.next();
    }

    @Override
    public void onBrace() {
        tokenizer.next();
    }

    @Override
    public void onOperation() {
        tokenizer.next();
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onUnexpected() {

    }

    @Override
    public TokensResponseWithError getResult() {
        return TokensResponseWithError.error(cause + " on " + position + " position");
    }
}
