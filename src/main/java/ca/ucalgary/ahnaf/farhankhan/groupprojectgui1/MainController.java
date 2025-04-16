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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.*;

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
            String selectedDay = DayChoice.getValue();
            if (selectedDay == null || selectedDay.trim().isEmpty()) {
                status_label.setText("Please select a valid day for your activity.");
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
                //String selectedDay = DayChoice.getValue();
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
    private Button WeeklyGoalsAchieved;

    @FXML
    private TextArea SpecialOutputs;

    @FXML
    private void handleWeeklyGoalsAchieved() {
        // Check if goals have been set
        if (Data.goal == null) {
            SpecialOutputs.setText("No goals have been set yet. Please set your goals before checking progress.");
            return;
        }

        // Check for any zero-value goals to prevent divide-by-zero
        if (Data.goal.getSleep() == 0 || Data.goal.getExercise() == 0 || Data.goal.getStudy() == 0 ||
                Data.goal.getWork() == 0 || Data.goal.getLeisure() == 0) {
            SpecialOutputs.setText("All goal values must be greater than 0 before checking weekly progress.");
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

        String output = "Percentage of Weekly Goals Achieved:\n" +
                String.format("Sleep: %.2f%%\n", percentSleep) +
                String.format("Exercise: %.2f%%\n", percentExercise) +
                String.format("Study: %.2f%%\n", percentStudy) +
                String.format("Work: %.2f%%\n", percentWork) +
                String.format("Leisure: %.2f%%\n", percentLeisure);

        SpecialOutputs.setText(output);
    }

    private String getDay() {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Day");
            dialog.setHeaderText("Day of the week (Monday-Sunday:");
            dialog.setContentText("Day:");

            Optional<String> result = dialog.showAndWait();

            // If user cancels the dialog
            if (!result.isPresent()) {
                return ""; // or null, depending on how you want to handle canceling
            }

            String dayOfWeek = result.get().toLowerCase();

            switch (dayOfWeek) {
                case "monday":
                case "tuesday":
                case "wednesday":
                case "thursday":
                case "friday":
                case "saturday":
                case "sunday":
                    status_label.setText("You entered " + capitalize(dayOfWeek) + ".");
                    return dayOfWeek;

                default:
                    showError("Invalid day entered. Must be a day of the week.");
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


    @FXML
    private Button DailyGoalsAchieved;

    @FXML
    private void handleDailyGoalsAchieved() {
        // Get the day from user input (assume a method or TextField for this)
        String day = getDay(); // Update this if day is selected through a GUI element
        if (day.isEmpty()) return; // If user cancelled or didn't enter anything

        // Retrieve the activity data for the selected day
        Map<String, Integer> dayMap = Data.getDayMap(day);

        // Check for missing day data
        if (dayMap == null) {
            showError("No data found for " + capitalize(day) + ".");
            return;
        }

        // Retrieve logged hours for the selected day
        int totalSleep = dayMap.getOrDefault("sleep", 0);
        int totalExercise = dayMap.getOrDefault("exercise", 0);
        int totalStudy = dayMap.getOrDefault("study", 0);
        int totalWork = dayMap.getOrDefault("work", 0);
        int totalLeisure = dayMap.getOrDefault("leisure", 0);

        // Retrieve daily goal values
        int GoalSleep = Data.goal.getSleep();
        int GoalExercise = Data.goal.getExercise();
        int GoalStudy = Data.goal.getStudy();
        int GoalWork = Data.goal.getWork();
        int GoalLeisure = Data.goal.getLeisure();

        // Calculate percentages
        double percentSleep = Math.min((totalSleep / (double) GoalSleep) * 100, 100);
        double percentExercise = Math.min((totalExercise / (double) GoalExercise) * 100, 100);
        double percentStudy = Math.min((totalStudy / (double) GoalStudy) * 100, 100);
        double percentWork = Math.min((totalWork / (double) GoalWork) * 100, 100);
        double percentLeisure = Math.min((totalLeisure / (double) GoalLeisure) * 100, 100);

        // Build the result string
        StringBuilder result = new StringBuilder();
        result.append("Percentage of Daily Goals Achieved for ").append(capitalize(day)).append(":\n");
        result.append(String.format("Sleep: %.2f%%\n", percentSleep));
        result.append(String.format("Exercise: %.2f%%\n", percentExercise));
        result.append(String.format("Study: %.2f%%\n", percentStudy));
        result.append(String.format("Work: %.2f%%\n", percentWork));
        result.append(String.format("Leisure: %.2f%%\n", percentLeisure));

        // Display in TextArea
        SpecialOutputs.setText(result.toString());
    }

    @FXML
    private void handleNumberGoalsAchieved() {
        String[] activities = {"sleep", "exercise", "study", "work", "leisure"};

        int GoalSleep = Data.goal.getSleep() * 7;
        int GoalExercise = Data.goal.getExercise() * 7;
        int GoalStudy = Data.goal.getStudy() * 7;
        int GoalWork = Data.goal.getWork() * 7;
        int GoalLeisure = Data.goal.getLeisure() * 7;

        Map<String, Integer> totalLogged = new HashMap<>();
        for (String activity : activities) {
            totalLogged.put(activity, 0);
        }

        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        for (String day : days) {
            Map<String, Integer> dayMap = Data.getDayMap(day);
            if (dayMap != null) {
                for (String activity : activities) {
                    totalLogged.put(activity, totalLogged.get(activity) + dayMap.getOrDefault(activity, 0));
                }
            }
        }

        Map<String, Integer> weeklyGoals = new HashMap<>();
        weeklyGoals.put("sleep", GoalSleep);
        weeklyGoals.put("exercise", GoalExercise);
        weeklyGoals.put("study", GoalStudy);
        weeklyGoals.put("work", GoalWork);
        weeklyGoals.put("leisure", GoalLeisure);

        int goalsAchieved = 0;
        int goalsNotAchieved = 0;

        for (String activity : activities) {
            if (totalLogged.get(activity) >= weeklyGoals.get(activity)) {
                goalsAchieved++;
            } else {
                goalsNotAchieved++;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Number of Goals Achieved / Not Achieved This Week:\n");
        result.append("Goals Achieved: ").append(goalsAchieved).append("\n");
        result.append("Goals Not Achieved: ").append(goalsNotAchieved);

        SpecialOutputs.setText(result.toString());
    }

    @FXML
    private void handleGoalsExceeded() {
        String day = getDay(); // user input day
        Map<String, Integer> actMap = Data.getDayMap(day); // returns Map<String, Integer>

        if (actMap != null) {
            StringBuilder output = new StringBuilder();
            output.append("Activities over goal on ").append(capitalize(day)).append(":\n");
            output.append(getActivityOverGoalDetails(actMap));
            SpecialOutputs.setText(output.toString());
        } else {
            showError("No data logged for " + capitalize(day) + ".");
        }
    }

    private String getActivityOverGoalDetails(Map<String, Integer> actMap) {
        String[] labels = {"sleep", "exercise", "study", "work", "leisure"};
        Map<String, Integer> goals = new HashMap<>();
        goals.put("sleep", Data.goal.getSleep());
        goals.put("exercise", Data.goal.getExercise());
        goals.put("study", Data.goal.getStudy());
        goals.put("work", Data.goal.getWork());
        goals.put("leisure", Data.goal.getLeisure());

        StringBuilder sb = new StringBuilder();
        for (String label : labels) {
            int actual = actMap.getOrDefault(label, 0);
            int goal = goals.getOrDefault(label, 0);

            if (actual > goal) {
                sb.append(capitalize(label))
                        .append(": ")
                        .append(actual)
                        .append("h (Goal: ")
                        .append(goal)
                        .append("h)\n");
            }
        }
        return sb.toString().isEmpty() ? "No goals exceeded on this day." : sb.toString();
    }

    @FXML
    private void handleRemainingTime() {
        String day = getDay(); // Get user-inputted day
        Map<String, Integer> actMap = Data.getDayMap(day); // Fetch activity map for the day

        if (actMap != null) {
            StringBuilder output = new StringBuilder();
            output.append("Time remaining to achieve goals on ").append(capitalize(day)).append(":\n");
            output.append(getTimeRemainingDetails(actMap));
            SpecialOutputs.setText(output.toString());
        } else {
            showError("No data logged for " + capitalize(day) + ".");
        }
    }

    private String getTimeRemainingDetails(Map<String, Integer> actMap) {
        String[] labels = {"sleep", "exercise", "study", "work", "leisure"};
        Map<String, Integer> goals = new HashMap<>();
        goals.put("sleep", Data.goal.getSleep());
        goals.put("exercise", Data.goal.getExercise());
        goals.put("study", Data.goal.getStudy());
        goals.put("work", Data.goal.getWork());
        goals.put("leisure", Data.goal.getLeisure());

        StringBuilder sb = new StringBuilder();
        for (String label : labels) {
            int actual = actMap.getOrDefault(label, 0);
            int goal = goals.getOrDefault(label, 0);
            int remaining = Math.max(0, goal - actual);

            sb.append(capitalize(label)).append(": ").append(remaining).append("h\n");
        }
        return sb.toString();
    }

    private boolean isValidDay(String day) {
        return List.of("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").contains(day);
    }

    @FXML
    private void handleEditActivities() {
        String day = getDay();
        if (day.isEmpty()) return; // User cancelled

        // Activity input fields
        TextField sleepField = new TextField();
        TextField exerciseField = new TextField();
        TextField studyField = new TextField();
        TextField workField = new TextField();
        TextField leisureField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Sleep hours:"), 0, 0);
        grid.add(sleepField, 1, 0);

        grid.add(new Label("Exercise hours:"), 0, 1);
        grid.add(exerciseField, 1, 1);

        grid.add(new Label("Study hours:"), 0, 2);
        grid.add(studyField, 1, 2);

        grid.add(new Label("Work hours:"), 0, 3);
        grid.add(workField, 1, 3);

        grid.add(new Label("Leisure hours:"), 0, 4);
        grid.add(leisureField, 1, 4);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Enter Activities");
        dialog.setHeaderText("Enter activity hours for " + capitalize(day));
        dialog.getDialogPane().setContent(grid);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButton) {
            try {
                int sleep = Integer.parseInt(sleepField.getText());
                int exercise = Integer.parseInt(exerciseField.getText());
                int study = Integer.parseInt(studyField.getText());
                int work = Integer.parseInt(workField.getText());
                int leisure = Integer.parseInt(leisureField.getText());

                int total = sleep + exercise + study + work + leisure;

                if (total > 24) {
                    showError("Total hours cannot exceed 24. You entered: " + total);
                    return;
                }

                Data.storeNewDay(day, sleep, exercise, study, work, leisure);
                ActivityDisplay.setText(Data.displayAllActivitiesGUI());
                status_label.setText("Activities updated for " + capitalize(day) + ".");

            } catch (NumberFormatException e) {
                showError("Please enter valid numbers for all activity fields.");
            }
        }
    }



}





