package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuScene extends Scene {
    private VBox root;

    public MenuScene(Parent root, int width, int height) {
        super(root, width, height);
        this.root = (VBox) root;
        this.root.setSpacing(20);
    }

    public void load() {
        HBox elevatorsBox = new HBox(20);
        Text elevatorsText = new Text("Elevators:");
        elevatorsText.setFont(Font.font(18));
        TextField elevatorsField = new TextField();
        elevatorsBox.getChildren().addAll(elevatorsText, elevatorsField);
        elevatorsBox.setAlignment(Pos.CENTER);

        HBox floorsBox = new HBox(20);
        Text floorsText = new Text("Floors:");
        floorsText.setFont(Font.font(18));
        TextField floorsField = new TextField();
        floorsBox.getChildren().addAll(floorsText, floorsField);
        floorsBox.setAlignment(Pos.CENTER);

        Button button = new Button("Start");
        button.setOnAction(event -> {
            DropShadow effect = new DropShadow();
            effect.setColor(Color.RED);
            int elevators = 0, floors = 0;
            boolean correct = true;
            try {
                elevators = Integer.parseInt(elevatorsField.getText());
                elevatorsField.setEffect(null);
            } catch(NumberFormatException e) {
                correct = false;
                elevatorsField.setEffect(effect);
            }
            try {
                floors = Integer.parseInt(floorsField.getText());
                floorsField.setEffect(null);
            } catch(NumberFormatException e) {
                correct = false;
                floorsField.setEffect(effect);
            }
            if(correct) {
                MainScene mainScene = new MainScene(new VBox(), 1280, 720);
                mainScene.load(elevators, floors);
                Program.setScene(mainScene);
            }
        });
        button.setAlignment(Pos.CENTER);

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(elevatorsBox, floorsBox, button);
    }
}
