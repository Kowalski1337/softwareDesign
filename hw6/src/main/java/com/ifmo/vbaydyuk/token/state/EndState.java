package com.ifmo.vbaydyuk.token.state;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Tokenizer;

public class EndState extends State {
    public EndState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void onNumber() {
        onAction();
    }

    @Override
    public void onBrace() {
        onAction();
    }

    @Override
    public void onOperation() {
        onAction();
    }

    @Override
    public void onEnd() {
        onAction();
    }

    @Override
    public void onUnexpected() {
        onAction();
    }

    @Override
    public TokensResponseWithError getResult() throws IllegalStateException {
        return TokensResponseWithError.success(tokenizer.getTokens());
    }

    private void onAction() {
        tokenizer.changeState(new ErrorState(tokenizer, "end exited", tokenizer.getPosition()));
    }
}
