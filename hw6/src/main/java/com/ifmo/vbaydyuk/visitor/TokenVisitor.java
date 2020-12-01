package com.ifmo.vbaydyuk.visitor;

import com.ifmo.vbaydyuk.response.TokensResponseWithError;
import com.ifmo.vbaydyuk.token.Brace;
import com.ifmo.vbaydyuk.token.NumberToken;
import com.ifmo.vbaydyuk.token.Operation;


public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Brace token);

    void visit(Operation token);

    TokensResponseWithError applyOnSuccess(TokensResponseWithError tokensResponseWithError);

    default TokensResponseWithError apply(TokensResponseWithError tokensResponseWithError) {
        if (!tokensResponseWithError.isSuccess())
            return tokensResponseWithError;
        return applyOnSuccess(tokensResponseWithError);
    }
}