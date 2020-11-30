package com.ifmo.vbaydyuk.application;

import com.ifmo.vbaydyuk.drawingApi.AwtDrawingApi;
import com.ifmo.vbaydyuk.graph.GraphFactory;

public class AwtDrawingApplication implements DrawingApplication {
    private final int width;
    private final int height;

    public AwtDrawingApplication(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        new GraphFactory().getGraph(new AwtDrawingApi(width, height, true)).drawGraph();
    }
}
