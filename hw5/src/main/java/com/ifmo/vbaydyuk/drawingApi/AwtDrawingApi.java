package com.ifmo.vbaydyuk.drawingApi;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AwtDrawingApi implements DrawingApi {
    private final int drawingAreaWidth;
    private final int drawingAreaHeight;
    private final Panel panel = new Panel();
    private final JFrame frame;

    public AwtDrawingApi(int drawingAreaWidth, int drawingAreaHeight, boolean exitOnClose) {
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
        frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(exitOnClose ? EXIT_ON_CLOSE : DO_NOTHING_ON_CLOSE);
        frame.setSize(drawingAreaWidth, drawingAreaHeight);
        frame.setResizable(false);
        frame.setVisible(true);
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
        panel.addShape(new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        panel.addShape(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }

    static class Panel extends JPanel {
        private final List<Shape> shapes = new ArrayList<>();

        private void addShape(Shape shape) {
            shapes.add(shape);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D graphics2D = (Graphics2D) graphics;
            for (Shape shape : shapes) {
                graphics2D.setPaint(Color.GREEN);
                if (shape instanceof Ellipse2D.Double) {
                    graphics2D.fill(shape);
                } else {
                    graphics2D.draw(shape);
                }
            }
        }
    }

}
