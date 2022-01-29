package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.ElevatorSystem;

public class MainScene extends Scene {
    VBox root;
    ElevatorSystem system;

    public MainScene(Parent root, int width, int height) {
        super(root, width, height);
        this.root = (VBox) root;
        this.root.setSpacing(20);
    }

    void load(int elevators, int floors) {
        system = new ElevatorSystem(elevators, floors);

        Text status = new Text(system.getFullStatus());
        status.setTextAlignment(TextAlignment.CENTER);

        Button nextStepButton = new Button(" --> ");
        nextStepButton.setOnAction(event -> {
            system.nextStep();
            status.setText(system.getFullStatus());
        });
        nextStepButton.setAlignment(Pos.CENTER);

        HBox newPersonBox = new HBox(20);

        TextField newPersonTextField = new TextField();

        Button newPersonButton = new Button(" + ");
        newPersonButton.setOnAction(event -> {
            String[] s = newPersonTextField.getText().split(" ");
            try {
                system.addPerson(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            } catch(Exception ignored) {}
            newPersonTextField.setText("");
            status.setText(system.getFullStatus());
        });
        newPersonBox.getChildren().addAll(newPersonTextField, newPersonButton);
        newPersonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(status, nextStepButton, newPersonBox);
        this.root.setAlignment(Pos.CENTER);
    }
}
