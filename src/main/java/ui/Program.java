package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Program extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Elevator Control System");
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        MenuScene menuScene = new MenuScene(new VBox(), 1280, 720);
        menuScene.load();
        setScene(menuScene);
        stage.show();
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
