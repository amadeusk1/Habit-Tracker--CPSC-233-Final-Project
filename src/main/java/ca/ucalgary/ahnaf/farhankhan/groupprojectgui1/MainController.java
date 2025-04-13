package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private ChoiceBox<String> DayChoice;

    @FXML
    private ChoiceBox<String> GoalActivityChoice;

    @FXML
    private Label dayText;

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
    private Button confirm;

    @FXML
    private TextArea GoalsDisplay;

    @FXML
    public void initialize() {
        // Populate the DayChoice menu
        DayChoice.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        // Populate the Goal / activity menu
        GoalActivityChoice.setItems(FXCollections.observableArrayList("Goals", "Activity"));

        // Set DayChoice to be hidden by default since initial selection is "Goal"
        DayChoice.setVisible(false);
        dayText.setVisible(false);


        // Listen for changes on GoalActivityChoice
        GoalActivityChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Show the DayChoice only if the user selects "Habit"
            if ("Activity".equals(newValue)) {
                DayChoice.setVisible(true);
                dayText.setVisible(true);
            } else {
                DayChoice.setVisible(false);
                dayText.setVisible(false);
            }
        });
    }


    @FXML
    void confirmAction(ActionEvent event) {
        // Get the selected values from goals
        String selectedGoal = GoalActivityChoice.getValue();
        int sleepV = 0, exerciseV = 0, studyV = 0, workV = 0, leisureV = 0;
        try {
            sleepV = Integer.parseInt(sleep.getText());
            exerciseV = Integer.parseInt(exercise.getText());
            studyV = Integer.parseInt(study.getText());
            workV = Integer.parseInt(work.getText());
            leisureV = Integer.parseInt(leisure.getText());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            // Optionally, display an error message to the user, e.g., using an Alert.
        }
        // if goals is selected
        if ("Goals".equals(selectedGoal)) {
            Goals goals = new Goals(sleepV,exerciseV,studyV,workV,leisureV);
        } else if ("Activity".equals(selectedGoal)) {
            String selectedDay = DayChoice.getValue();
            Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);

        } else {
            System.out.println("Select Goal / Habit");
        }
    }

}
