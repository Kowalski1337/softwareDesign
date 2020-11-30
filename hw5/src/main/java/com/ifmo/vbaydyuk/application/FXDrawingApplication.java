package com.ifmo.vbaydyuk.application;

import com.ifmo.vbaydyuk.drawingApi.DrawingApi;
import com.ifmo.vbaydyuk.drawingApi.JavaFxDrawingApi;
import com.ifmo.vbaydyuk.graph.GraphFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXDrawingApplication extends Application implements DrawingApplication {

    @Override
    public void run() {
        Application.launch(FXDrawingApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        try (DrawingApi api = new JavaFxDrawingApi(primaryStage, gc, 600, 600)) {
            new GraphFactory().getGraph(api).drawGraph();
        } catch (Exception e) {
            System.out.println("Cannot close api");
        } finally {
            primaryStage.show();
        }
    }

}
