package com.ifmo.vbaydyuk.response;

import com.ifmo.vbaydyuk.token.Token;

import java.util.List;

public class TokensResponseWithError {
    private final String errorMessage;
    private final List<Token> tokens;

    private TokensResponseWithError(List<Token> tokens, String errorMessage) {
        this.errorMessage = errorMessage;
        this.tokens = tokens;
    }

    public static TokensResponseWithError success(List<Token> tokens) {
        return new TokensResponseWithError(tokens, null);
    }

    public static TokensResponseWithError error(String errorMessage) {
        return new TokensResponseWithError(null, errorMessage);
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return "TokensResponseWithError{" +
                (isSuccess() ? "Success: " + getTokens() : "Error : " + getErrorMessage()) +
                "}";
    }
}
