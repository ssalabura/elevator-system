package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Program extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Elevator Control System");
        MenuScene menuScene = new MenuScene(new VBox(), 1280, 720);
        menuScene.load();
        setScene(menuScene);
        stage.show();
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
