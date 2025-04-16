//package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;
//
//import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
//import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.input.ContextMenuEvent;
//import javafx.scene.layout.Pane;
//
//public class MainController {
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
//
//    @FXML
//    private ChoiceBox<String> DayChoice;
//
//    @FXML
//    private ChoiceBox<String> GoalActivityChoice;
//
//    @FXML
//    private Label systemUpdates;
//
//    @FXML
//    private Label dayText;
//
//    @FXML
//    private TextField exercise;
//
//    @FXML
//    private TextField leisure;
//
//    @FXML
//    private TextField sleep;
//
//    @FXML
//    private TextField study;
//
//    @FXML
//    private TextField work;
//
//    @FXML
//    private Button confirm;
//
//    @FXML
//    private TextArea GoalsDisplay;
//
//    @FXML
//    public void initialize() {
//        // Populate the DayChoice menu
//        DayChoice.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
//
//        // Populate the Goal / activity menu
//        GoalActivityChoice.setItems(FXCollections.observableArrayList("Goals", "Activity"));
//
//        // Set DayChoice to be hidden by default since initial selection is "Goal"
//        DayChoice.setVisible(false);
//        dayText.setVisible(false);
//
//
//        // Listen for changes on GoalActivityChoice
//        GoalActivityChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            // Show the DayChoice only if the user selects "Habit"
//            if ("Activity".equals(newValue)) {
//                DayChoice.setVisible(true);
//                dayText.setVisible(true);
//            } else {
//                DayChoice.setVisible(false);
//                dayText.setVisible(false);
//            }
//        });
//    }
//
//
//    @FXML
//    void confirmAction(ActionEvent event) {
//        // Get the selected values from goals
//        String selectedGoal = GoalActivityChoice.getValue();
//        try {
//            int sleepV = Integer.parseInt(sleep.getText());
//            int exerciseV = Integer.parseInt(exercise.getText());
//            int studyV = Integer.parseInt(study.getText());
//            int workV = Integer.parseInt(work.getText());
//            int leisureV = Integer.parseInt(leisure.getText());
//            // check if inputs are positive
//            if (sleepV < 0 || exerciseV < 0 || studyV < 0 || workV < 0 || leisureV < 0) {
//                systemUpdates.setText("All inputs must be positive numbers.");
//                return;
//            }
//            // sum total inputs
//            int sumTotal = sleepV + exerciseV + studyV + workV + leisureV;
//
//            // if goals is selected
//            if ("Goals".equals(selectedGoal)) {
//                if (sumTotal <= 24) {
//                    Goals goals = new Goals(sleepV, exerciseV, studyV, workV, leisureV);
//                    GoalsDisplay.setText(goals.toString());
//                    systemUpdates.setText("Goals logged successfully");
//                } else {
//                    systemUpdates.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
//                }
//            } else if ("Activity".equals(selectedGoal)) {
//                String selectedDay = DayChoice.getValue();
//                if (sumTotal <= 24) {
//                    Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
//                    systemUpdates.setText("Activities logged successfully");
//                } else {
//                    systemUpdates.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
//                }
//
//            } else {
//                systemUpdates.setText("Select Goal / Activity");
//            }
//
//
//        } catch (NumberFormatException e) {
//            systemUpdates.setText("Invalid input. Please enter a valid integer for all inputs .");
//            // Optionally, display an error message to the user, e.g., using an Alert.
//        }
//
//    }
//
//}




package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileLoader;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileSaver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import javafx.scene.paint.Color;

import static ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data.goal;


public class MainController {

    private static Data data = new Data();

//    << formating sting code from menu like jonathan did in yt video >>


    @FXML
    private ChoiceBox<String> DayChoice;

    @FXML
    private ChoiceBox<String> GoalActivityChoice;

    @FXML
    private TextArea GoalsDisplay;

    @FXML
    private Button confirm;

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
    private Label status_label;

    @FXML
    private TextField work;

    @FXML
    void editActivities(ActionEvent event) {

    }

    @FXML
    void editGoals(ActionEvent event) {

    }



    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void load(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showOpenDialog(new Stage());
        load(file);
        menuViewAllActivities();
//        menuViewAllGoals();
        GoalsDisplay.setText(goal.toString());
    }

    public static void menuViewAllActivities() {
        Data.displayAllActivities();
    }

//    public static void menuViewAllGoals() {
//        System.out.println("\nCurrent Daily Goals:");
//        System.out.println("Sleep: " + Data.goal.getSleep() + " hours per day");
//        System.out.println("Exercise: " + Data.goal.getExercise() + " hours per day");
//        System.out.println("Study: " + Data.goal.getStudy() + " hours per day");
//        System.out.println("Work: " + Data.goal.getStudy() + " hours per day");
//        System.out.println("Leisure: " + Data.goal.getLeisure() + " hours per day");
//    }


    private void load(File file) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        Data data = FileLoader.load(file);
        if (data == null) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to load data from file %s%n", file));
        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Loaded data from file %s%n", file));
            MainController.data = data;
        }
    }

    @FXML
    void save(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showSaveDialog(new Stage());
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        if (FileSaver.save(file, data)) {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Saved to file %s%n", file));
        } else {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to save to file %s%n", file));
        }
    }

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
        try {
            int sleepV = Integer.parseInt(sleep.getText());
            int exerciseV = Integer.parseInt(exercise.getText());
            int studyV = Integer.parseInt(study.getText());
            int workV = Integer.parseInt(work.getText());
            int leisureV = Integer.parseInt(leisure.getText());
            // check if inputs are positive
            if (sleepV < 0 || exerciseV < 0 || studyV < 0 || workV < 0 || leisureV < 0) {
                status_label.setText("All inputs must be positive numbers.");
                return;
            }
            // sum total inputs
            int sumTotal = sleepV + exerciseV + studyV + workV + leisureV;

            // if goals is selected
            if ("Goals".equals(selectedGoal)) {
                if (sumTotal <= 24) {
                    Goals goals = new Goals(sleepV, exerciseV, studyV, workV, leisureV);
                    GoalsDisplay.setText(goals.toString());
                    status_label.setText("Goals logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }
            } else if ("Activity".equals(selectedGoal)) {
                String selectedDay = DayChoice.getValue();
                if (sumTotal <= 24) {
                    Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    status_label.setText("Activities logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }

            } else {
                status_label.setText("Select Goal / Activity");
            }


        } catch (NumberFormatException e) {
            status_label.setText("Invalid input. Please enter a valid integer for all inputs .");
            // Optionally, display an error message to the user, e.g., using an Alert.
        }

    }

}





