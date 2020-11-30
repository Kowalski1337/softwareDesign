package com.ifmo.vbaydyuk.console;

import com.ifmo.vbaydyuk.drawingApi.DrawingApi;
import com.ifmo.vbaydyuk.drawingApi.JavaFxDrawingApi;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxConsole extends Application {
    private DrawingApi api;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        api = new JavaFxDrawingApi(primaryStage, gc, 600, 600);

        new Thread(() -> new Console().run(api)).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (api != null) {
            api.close();
            api = null;
        }
    }

    public static void main(String[] args) {
        launch(FxConsole.class);
    }
}
