package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.token.Tokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Bean
    Tokenizer getTokenizer() {
        return new Tokenizer();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
