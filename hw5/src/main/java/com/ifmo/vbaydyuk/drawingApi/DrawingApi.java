package com.ifmo.vbaydyuk.drawingApi;

public interface DrawingApi extends AutoCloseable{
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void drawCircle(double centerX, double centerY, double radius);
    void drawLine(double x1, double y1, double x2, double y2);
}
