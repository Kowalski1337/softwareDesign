package com.ifmo.vbaydyuk.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GraphDrawRunner implements CommandLineRunner {

    private final DrawingApplication drawingApplication;

    public GraphDrawRunner(DrawingApplication drawingApplication) {
        this.drawingApplication = drawingApplication;
    }

    @Override
    public void run(String... args) {
        drawingApplication.run();
    }
}
