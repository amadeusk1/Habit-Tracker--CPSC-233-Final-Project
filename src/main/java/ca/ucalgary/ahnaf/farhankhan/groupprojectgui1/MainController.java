package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Day;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;

import static ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data.*;


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
    private TextArea ActivityDisplay;

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
        ActivityDisplay.setText(Data.displayAllActivitiesGUI());
//       menuViewAllGoals();
//        menuViewAllActivities();
        GoalsDisplay.setText(goal.toString());
    }

//    public void menuViewAllActivities() {
//        Data.displayAllActivitiesGUI();
//    }

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
                    Data.goal = goals;
                    GoalsDisplay.setText(goals.toString());
                    status_label.setText("Goals logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }
            } else if ("Activity".equals(selectedGoal)) {
                String selectedDay = DayChoice.getValue();
                if (sumTotal <= 24) {
                    Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    storeNewDay(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    ActivityDisplay.setText(Data.displayAllActivitiesGUI());
                    status_label.setText("Activities logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }

            } else {
                status_label.setText("Select Goal / Activity");
            }


        } catch (NumberFormatException e) {
            status_label.setText("Invalid input. Please enter a valid integer for all inputs .");
        }

    }

    @FXML
    private Button weeklygoalsachieved;

    @FXML
    private TextArea specialoutputs;

    @FXML
    private void handleWeeklyGoalsAchieved() {
        // Check if goals have been set
        if (Data.goal == null) {
            specialoutputs.setText("No goals have been set yet. Please set your goals before checking progress.");
            return;
        }

        // Check for any zero-value goals to prevent divide-by-zero
        if (Data.goal.getSleep() == 0 || Data.goal.getExercise() == 0 || Data.goal.getStudy() == 0 ||
                Data.goal.getWork() == 0 || Data.goal.getLeisure() == 0) {
            specialoutputs.setText("All goal values must be greater than 0 before checking weekly progress.");
            return;
        }

        int totalSleep = 0, totalExercise = 0, totalStudy = 0, totalWork = 0, totalLeisure = 0;
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        for (String day : days) {
            Map<String, Integer> dayMap = Data.getDayMap(day);
            if (dayMap != null) {
                totalSleep += dayMap.getOrDefault("sleep", 0);
                totalExercise += dayMap.getOrDefault("exercise", 0);
                totalStudy += dayMap.getOrDefault("study", 0);
                totalWork += dayMap.getOrDefault("work", 0);
                totalLeisure += dayMap.getOrDefault("leisure", 0);
            }
        }

        int GoalSleep = Data.goal.getSleep() * 7;
        int GoalExercise = Data.goal.getExercise() * 7;
        int GoalStudy = Data.goal.getStudy() * 7;
        int GoalWork = Data.goal.getWork() * 7;
        int GoalLeisure = Data.goal.getLeisure() * 7;

        double percentSleep = (totalSleep / (double) GoalSleep) * 100;
        double percentExercise = (totalExercise / (double) GoalExercise) * 100;
        double percentStudy = (totalStudy / (double) GoalStudy) * 100;
        double percentWork = (totalWork / (double) GoalWork) * 100;
        double percentLeisure = (totalLeisure / (double) GoalLeisure) * 100;

        if (percentSleep > 100) percentSleep = 100;
        if (percentExercise > 100) percentExercise = 100;
        if (percentStudy > 100) percentStudy = 100;
        if (percentWork > 100) percentWork = 100;
        if (percentLeisure > 100) percentLeisure = 100;

        String output = "\nPercentage of Weekly Goals Achieved:\n" +
                String.format("Sleep: %.2f%%\n", percentSleep) +
                String.format("Exercise: %.2f%%\n", percentExercise) +
                String.format("Study: %.2f%%\n", percentStudy) +
                String.format("Work: %.2f%%\n", percentWork) +
                String.format("Leisure: %.2f%%\n", percentLeisure);

        specialoutputs.setText(output);
    }


}





