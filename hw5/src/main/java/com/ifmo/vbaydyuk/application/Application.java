package com.ifmo.vbaydyuk.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application extends SpringApplication {

    private static final String AWT = "awt";
    private static final String JAVA_FX = "javaFX";

    @Value("${width}")
    private int width;
    @Value("${height}")
    private int height;
    @Value("${drawing.framework}")
    private String drawingApi;

    @Bean
    public DrawingApplication getDrawingApplication() {
        switch (drawingApi) {
            case AWT:
                return new AwtDrawingApplication(width, height);
            case JAVA_FX:
                return new FXDrawingApplication();
            default:
                throw new IllegalStateException("unknown drawing framework");
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
