package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private Pane DayChoice;

    @FXML
    private MenuButton GoalActivityChoice;

    @FXML
    private TextField exercise;

    @FXML
    private TextField leisure;

    @FXML
    private TextField sleep;

    @FXML
    private TextField study;

    @FXML
    private TextField work;

}
