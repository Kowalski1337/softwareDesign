package com.ifmo.vbaydyuk.token.state;

import com.ifmo.vbaydyuk.token.Add;
import com.ifmo.vbaydyuk.token.CloseBrace;
import com.ifmo.vbaydyuk.token.Div;
import com.ifmo.vbaydyuk.token.Mul;
import com.ifmo.vbaydyuk.token.OpenBrace;
import com.ifmo.vbaydyuk.token.Sub;
import com.ifmo.vbaydyuk.token.Tokenizer;

public class StartState extends State {

    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void onNumber() {
        tokenizer.changeState(new NumberState(tokenizer));
        tokenizer.next();
    }

    @Override
    public void onBrace() {
        switch (tokenizer.current()) {
            case '(':
                tokenizer.saveToken(new OpenBrace());
                tokenizer.next();
                break;
            case ')':
                tokenizer.saveToken(new CloseBrace());
                tokenizer.next();
                break;
            default:
                tokenizer.changeState(new ErrorState(tokenizer, "'(' or ')' expected in brace state", tokenizer.getPosition()));
                tokenizer.next();
        }
    }

    @Override
    public void onOperation() {
        switch (tokenizer.current()) {
            case '-':
                tokenizer.saveToken(new Sub());
                tokenizer.next();
                break;
            case '+':
                tokenizer.saveToken(new Add());
                tokenizer.next();
                break;
            case '*':
                tokenizer.saveToken(new Mul());
                tokenizer.next();
                break;
            case '/':
                tokenizer.saveToken(new Div());
                tokenizer.next();
                break;
            default:
                tokenizer.changeState(new ErrorState(tokenizer, "'+', '-', '*' or '/' expected in operation state", tokenizer.getPosition()));
                tokenizer.next();
        }
    }

}
