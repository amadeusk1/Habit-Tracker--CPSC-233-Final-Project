package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private MenuButton DayChoice;

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

    @FXML
    public void initialize() {
//        // Populate the DayChoice menu
//        dayChoice(DayChoice);
//
//        // Populate the Goal / activity menu
//        goalOrActivity(GoalActivityChoice);
    }

//    // Method to add choices to the goal/ activity selection
//    private void goalOrActivity(MenuButton menuButton) {
//        String[] activities = {"Sleep", "Exercise", "Study", "Work", "Leisure"};
//        for (int i = 0; i < activities.length; i++) {
//            menuButton.getItems().add(new MenuItem(activities[i])); // Add choices
//        }
//    }
//
//    // Method to add choices to the days
//    private void dayChoice(MenuButton menuButton) {
//        String[] days ={"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//        for (int i = 0; i < days.length; i++) {
//            menuButton.getItems().add(new MenuItem(days[i])); // Add choices
//        }
//    }


}
