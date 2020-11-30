package com.ifmo.vbaydyuk.drawingApi;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class JavaFxDrawingApi implements DrawingApi {

    private final int drawingAreaWidth;
    private final int drawingAreaHeight;
    private final GraphicsContext graphicsContext;
    private final Stage stage;

    public JavaFxDrawingApi(Stage stage, GraphicsContext graphicsContext, int drawingAreaWidth, int drawingAreaHeight) {
        this.stage = stage;
        this.graphicsContext = graphicsContext;
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
    }

    @Override
    public long getDrawingAreaWidth() {
        return drawingAreaWidth;
    }

    @Override
    public long getDrawingAreaHeight() {
        return drawingAreaHeight;
    }

    @Override
    public void drawCircle(double centerX, double centerY, double radius) {
        graphicsContext.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void close() {
        stage.hide();
        stage.close();
    }

}
