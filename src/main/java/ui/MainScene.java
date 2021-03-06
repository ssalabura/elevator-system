package ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;

import java.util.*;

public class MainScene extends Scene {
    final int FLOORS_UI_THRESHOLD = 20;

    FlowPane root;
    GridPane grid;
    List<Text> peopleTexts;
    VBox options;
    ElevatorSystem system;
    Random random;

    int floors;
    int cellWidth;
    int cellHeight;

    public MainScene(Parent root, int width, int height) {
        super(root, width, height);
        this.root = (FlowPane) root;
        this.root.setHgap(20);
        this.root.setVgap(20);
        peopleTexts = new ArrayList<>();
        random = new Random();
    }

    void load(int elevators, int floors) {
        system = new ElevatorSystem(elevators, floors);
        this.floors = floors;

        cellWidth = Math.min(800/(elevators+1),100);
        cellHeight = Math.min(700/(floors+1),100);
        cellWidth = Math.min(cellWidth, cellHeight*2);
        cellHeight = Math.min(cellHeight, cellWidth*2);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for(int i=0; i<=elevators; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(cellWidth));
        }
        grid.getColumnConstraints().add(new ColumnConstraints(125));
        grid.getColumnConstraints().add(new ColumnConstraints(75));

        // floor numbers and people waiting
        for(int i=0; i<=floors; i++) {
            grid.getRowConstraints().add(new RowConstraints(cellHeight));

            Text floorText = new Text(String.valueOf(floors-i));
            floorText.setFont(Font.font(cellHeight-2));
            grid.add(floorText,0,i);
            GridPane.setHalignment(floorText, HPos.CENTER);

            Text peopleText = new Text();
            grid.add(peopleText,elevators+1,i);
            GridPane.setMargin(peopleText, new Insets(10));

            peopleTexts.add(peopleText);
        }

        // adding new person boxes
        if(floors <= FLOORS_UI_THRESHOLD) {
            for(int i=0; i<=floors; i++) {
                int finalI = i;
                HBox newPersonBox = new HBox(10);
                TextField newPersonTextField = new TextField();
                Button newPersonButton = new Button("+");
                newPersonButton.setOnAction(event -> {
                    String s = newPersonTextField.getText();
                    try {
                        system.addPerson(floors - finalI, Integer.parseInt(s));
                    } catch (Exception ignored) {
                    }
                    newPersonTextField.setText("");
                    updatePeople();
                });
                newPersonBox.getChildren().addAll(newPersonTextField, newPersonButton);
                newPersonBox.setAlignment(Pos.CENTER);
                grid.add(newPersonBox, elevators + 2, i);
            }
        }
        updateElevators();

        options = new VBox(20);
        options.setAlignment(Pos.CENTER);

        VBox nextStepBox = new VBox(10);

        Button nextStepButton = new Button("Next Step");
        nextStepButton.setOnAction(event -> {
            system.nextStep();
            updateElevators();
            updatePeople();
        });
        nextStepButton.setAlignment(Pos.CENTER);

        HBox autoplayBox = new HBox(10);
        CheckBox autoplayCheckBox = new CheckBox();
        autoplayCheckBox.selectedProperty().addListener(new ChangeListener<>() {
            Timer timer;

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            system.nextStep();
                            Platform.runLater(() -> {
                                updateElevators();
                                updatePeople();
                            });
                        }
                    }, 0, 200);
                } else {
                    timer.cancel();
                }
            }
        });
        Text autoPlayText = new Text("Autoplay");

        autoplayBox.getChildren().addAll(autoplayCheckBox, autoPlayText);
        autoplayBox.setAlignment(Pos.CENTER);
        nextStepBox.getChildren().addAll(nextStepButton, autoplayBox);
        nextStepBox.setAlignment(Pos.CENTER);
        options.getChildren().add(nextStepBox);

        VBox newPersonBox = new VBox(10);
        if(floors > FLOORS_UI_THRESHOLD) {
            HBox textFieldBox = new HBox(10);
            textFieldBox.setPrefWidth(100);
            TextField fromTextField = new TextField();
            Text arrowText = new Text(" --> ");
            TextField toTextField = new TextField();
            textFieldBox.getChildren().addAll(fromTextField, arrowText, toTextField);

            Button newPersonButton = new Button("Add New Person");
            newPersonButton.setOnAction(event -> {
                try {
                    system.addPerson(Integer.parseInt(fromTextField.getText()), Integer.parseInt(toTextField.getText()));
                } catch (Exception ignored) {
                }
                fromTextField.setText("");
                toTextField.setText("");
                updatePeople();
            });

            newPersonBox.getChildren().addAll(textFieldBox, newPersonButton);
        }

        Button newRandomPersonButton = new Button("Add Random Person");
        newRandomPersonButton.setOnAction(event -> {
            system.addPerson(random.nextInt(floors), random.nextInt(floors));
            updatePeople();
        });

        newPersonBox.getChildren().add(newRandomPersonButton);
        newPersonBox.setAlignment(Pos.CENTER);
        options.getChildren().add(newPersonBox);

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> {
            MenuScene menuScene = new MenuScene(new VBox(), 1280, 720);
            menuScene.load();
            Program.setScene(menuScene);
        });
        backButton.setAlignment(Pos.CENTER);
        options.getChildren().add(backButton);

        root.getChildren().addAll(grid, options);
        this.root.setAlignment(Pos.CENTER);
    }

    void updateElevators() {
        List<ElevatorStatus> statuses = system.getStatus();
        grid.getChildren().removeIf(node -> node instanceof Rectangle);

        for(int i=0; i<statuses.size(); i++) {
            ElevatorStatus status = statuses.get(i);
            Rectangle r = new Rectangle();
            r.setHeight(cellHeight-4);
            r.setWidth(cellWidth-4);
            r.setStroke(Paint.valueOf("black"));
            if(status.doors == Doors.OPEN) {
                r.setFill(Color.LIGHTGRAY);
            } else {
                r.setFill(Color.GRAY);
            }
            grid.add(r,i+1,this.floors-status.currentFloor);
            GridPane.setHalignment(r, HPos.CENTER);
        }
    }

    void updatePeople() {
        for(Text text : peopleTexts) {
            text.setText("");
        }

        List<Person> people = system.getPeople();
        int[] howMany = new int[floors+1];
        for(Person person : people) {
            if(person.getElevator() == null) {
                int from = person.getFrom();
                howMany[from]++;
                if(howMany[from] > 3) {
                    peopleTexts.get(floors-from).setText(howMany[from] + " people");
                    continue;
                }
                String currentText = peopleTexts.get(floors-from).getText();
                String newText = String.valueOf(Direction.getChar(person.getDirection())) + person.getTo();
                peopleTexts.get(floors-from).setText(currentText + " " + newText);
            }
        }
    }
}
