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

    private Data data = new Data();

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
        ActivityDisplay.setText(data.displayAllActivitiesGUI());
        Goals currentGoals = Goals.getInstance();
        GoalsDisplay.setText(currentGoals.toString());
    }


    private void load(File file) {
        status_label.setTextFill(Color.BLACK);
        status_label.setText("");
        data.clear(); //Clears existing data

        Data data = FileLoader.load(file);
        if (data == null) {
            status_label.setTextFill(Color.RED);
            status_label.setText(String.format("Failed to load data from file %s%n", file));
        } else {
            status_label.setTextFill(Color.GREEN);
            status_label.setText(String.format("Loaded data from file %s%n", file));
            this.data = data;
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
                    // Initialize the global Goals instance.
                    Goals.initialize(sleepV, exerciseV, studyV, workV, leisureV);
                    //Goals goals = new Goals(sleepV, exerciseV, studyV, workV, leisureV);
                    //Data.goal = goals;
                    Goals currentGoals = Goals.getInstance();
                    GoalsDisplay.setText(currentGoals.toString());
                    status_label.setText("Goals logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }
            } else if ("Activity".equals(selectedGoal)) {
                String selectedDay = DayChoice.getValue();
                if (selectedDay == null || selectedDay.trim().isEmpty()) {
                    status_label.setText("Please select a valid day for your activity.");
                    return;
                }
                //String selectedDay = DayChoice.getValue();
                if (sumTotal <= 24) {
                    Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    data.storeNewDay(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    ActivityDisplay.setText(data.displayAllActivitiesGUI());
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
        if (Goals.getInstance() == null) {
            SpecialOutputs.setText("No goals have been set yet. Please set your goals before checking progress.");
            return;
        }

        int totalSleep = 0, totalExercise = 0, totalStudy = 0, totalWork = 0, totalLeisure = 0;
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        for (String day : days) {
            Map<String, Integer> dayMap = data.getDayMap(day);
            if (dayMap != null) {
                totalSleep += dayMap.getOrDefault("sleep", 0);
                totalExercise += dayMap.getOrDefault("exercise", 0);
                totalStudy += dayMap.getOrDefault("study", 0);
                totalWork += dayMap.getOrDefault("work", 0);
                totalLeisure += dayMap.getOrDefault("leisure", 0);
            }
        }

        int goalSleep = Goals.getInstance().getSleep() * 7;
        int goalExercise = Goals.getInstance().getExercise() * 7;
        int goalStudy = Goals.getInstance().getStudy() * 7;
        int goalWork = Goals.getInstance().getWork() * 7;
        int goalLeisure = Goals.getInstance().getLeisure() * 7;

        double percentSleep = 0;
        double percentExercise = 0;
        double percentStudy = 0;
        double percentWork = 0;
        double percentLeisure = 0;

        if (goalSleep != 0) {
            percentSleep = (totalSleep / (double) goalSleep) * 100;
        }
        if (goalExercise != 0) {
            percentExercise = (totalExercise / (double) goalExercise) * 100;
        }
        if (goalStudy != 0) {
            percentStudy = (totalStudy / (double) goalStudy) * 100;
        }
        if (goalWork != 0) {
            percentWork = (totalWork / (double) goalWork) * 100;
        }
        if (goalLeisure != 0) {
            percentLeisure = (totalLeisure / (double) goalLeisure) * 100;
        }

        // Cap values at 100
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
        // Get the day from user input
        String day = getDay(); // Update this if day is selected through a GUI element
        if (day.isEmpty()) return; // If user cancelled or didn't enter anything

        // Retrieve the activity data for the selected day
        Map<String, Integer> dayMap = data.getDayMap(day);

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
        int goalSleep = Goals.getInstance().getSleep();
        int goalExercise = Goals.getInstance().getExercise();
        int goalStudy = Goals.getInstance().getStudy();
        int goalWork = Goals.getInstance().getWork();
        int goalLeisure = Goals.getInstance().getLeisure();

        // Calculate percentages without using inline conditionals
        double percentSleep = 0;
        double percentExercise = 0;
        double percentStudy = 0;
        double percentWork = 0;
        double percentLeisure = 0;

        // check if if 0 entry and calculate precentage
        if (goalSleep != 0) {
            percentSleep = (totalSleep / (double) goalSleep) * 100;
            if (percentSleep > 100) percentSleep = 100;
        }

        if (goalExercise != 0) {
            percentExercise = (totalExercise / (double) goalExercise) * 100;
            if (percentExercise > 100) percentExercise = 100;
        }

        if (goalStudy != 0) {
            percentStudy = (totalStudy / (double) goalStudy) * 100;
            if (percentStudy > 100) percentStudy = 100;
        }

        if (goalWork != 0) {
            percentWork = (totalWork / (double) goalWork) * 100;
            if (percentWork > 100) percentWork = 100;
        }

        if (goalLeisure != 0) {
            percentLeisure = (totalLeisure / (double) goalLeisure) * 100;
            if (percentLeisure > 100) percentLeisure = 100;
        }

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

        int GoalSleep = Goals.getInstance().getSleep() * 7;
        int GoalExercise = Goals.getInstance().getExercise() * 7;
        int GoalStudy = Goals.getInstance().getStudy() * 7;
        int GoalWork = Goals.getInstance().getWork() * 7;
        int GoalLeisure = Goals.getInstance().getLeisure() * 7;

        Map<String, Integer> totalLogged = new HashMap<>();
        for (String activity : activities) {
            totalLogged.put(activity, 0);
        }

        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        for (String day : days) {
            Map<String, Integer> dayMap = data.getDayMap(day);
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
        Map<String, Integer> actMap = data.getDayMap(day); // returns Map<String, Integer>

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
        goals.put("sleep", Goals.getInstance().getSleep());
        goals.put("exercise", Goals.getInstance().getExercise());
        goals.put("study", Goals.getInstance().getStudy());
        goals.put("work", Goals.getInstance().getWork());
        goals.put("leisure", Goals.getInstance().getLeisure());

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
        Map<String, Integer> actMap = data.getDayMap(day); // Fetch activity map for the day

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
        goals.put("sleep", Goals.getInstance().getSleep());
        goals.put("exercise", Goals.getInstance().getExercise());
        goals.put("study", Goals.getInstance().getStudy());
        goals.put("work", Goals.getInstance().getWork());
        goals.put("leisure", Goals.getInstance().getLeisure());

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

                data.storeNewDay(day, sleep, exercise, study, work, leisure);
                ActivityDisplay.setText(data.displayAllActivitiesGUI());
                status_label.setText("Activities updated for " + capitalize(day) + ".");

            } catch (NumberFormatException e) {
                showError("Please enter valid numbers for all activity fields.");
            }
        }
    }

    @FXML
    private void HandleEditGoals() {
        TextField sleepField = new TextField();
        TextField exerciseField = new TextField();
        TextField studyField = new TextField();
        TextField workField = new TextField();
        TextField leisureField = new TextField();

        Goals current = Goals.getInstance();
        if (current != null) {
            sleepField.setText(String.valueOf(current.getSleep()));
            exerciseField.setText(String.valueOf(current.getExercise()));
            studyField.setText(String.valueOf(current.getStudy()));
            workField.setText(String.valueOf(current.getWork()));
            leisureField.setText(String.valueOf(current.getLeisure()));
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Sleep:"), 0, 0);
        grid.add(sleepField, 1, 0);
        grid.add(new Label("Exercise:"), 0, 1);
        grid.add(exerciseField, 1, 1);
        grid.add(new Label("Study:"), 0, 2);
        grid.add(studyField, 1, 2);
        grid.add(new Label("Work:"), 0, 3);
        grid.add(workField, 1, 3);
        grid.add(new Label("Leisure:"), 0, 4);
        grid.add(leisureField, 1, 4);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Daily Goals");
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int sleep = Integer.parseInt(sleepField.getText());
                int exercise = Integer.parseInt(exerciseField.getText());
                int study = Integer.parseInt(studyField.getText());
                int work = Integer.parseInt(workField.getText());
                int leisure = Integer.parseInt(leisureField.getText());

                int sumTotal = sleep + exercise + study + work + leisure;
                if (sumTotal <= 24) {
                    Goals.initialize(sleep, exercise, study, work, leisure); // must exist in your Goals class
                    Goals currentGoals = Goals.getInstance();
                    GoalsDisplay.setText(currentGoals.toString());
                    status_label.setText("Goals logged successfully.");
                } else {
                    showError("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }
            } catch (NumberFormatException e) {
                showError("Invalid input. Please enter valid whole numbers.");
            }
        }
    }


    @FXML
    private Button TotalHoursLogged;

    @FXML
    private Button TotalHoursLogged_Day;

    @FXML
    private Button MaxandMinActivity;


    @FXML
    private void handleTotalHoursLogged() {
        // Initialize counters
        int total = 0, sleep = 0, exercise = 0, study = 0, work = 0, leisure = 0;

        // Add up activity hours from all days
        for (Day d : data.getDays()) {
            if (d instanceof Activity act) {
                sleep += act.getSleep();
                exercise += act.getExercise();
                study += act.getStudy();
                work += act.getWork();
                leisure += act.getLeisure();
            }
        }

        total = sleep + exercise + study + work + leisure;

        // Show results in Special Output box
        StringBuilder output = new StringBuilder();
        output.append("Total Hours Logged (All Days):\n");
        output.append("Total: ").append(total).append(" hours\n\n");
        output.append("Sleep: ").append(sleep).append(" hours\n");
        output.append("Exercise: ").append(exercise).append(" hours\n");
        output.append("Study: ").append(study).append(" hours\n");
        output.append("Work: ").append(work).append(" hours\n");
        output.append("Leisure: ").append(leisure).append(" hours");

        SpecialOutputs.setText(output.toString());
    }


    @FXML
    private void handleTotalHoursLogged_Day() {
        String day = DayChoice.getValue();

        if (day == null || day.trim().isEmpty()) {
            status_label.setText("Please select a valid day.");
            return;
        }

        day = day.toLowerCase(); // match backend format

        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                int total = act.getSleep() + act.getExercise() + act.getStudy() + act.getWork() + act.getLeisure();

                StringBuilder output = new StringBuilder();
                output.append("Total Hours for ").append(capitalize(day)).append(":\n");
                output.append("Total: ").append(total).append(" hours\n\n");
                output.append("Sleep: ").append(act.getSleep()).append(" hours\n");
                output.append("Exercise: ").append(act.getExercise()).append(" hours\n");
                output.append("Study: ").append(act.getStudy()).append(" hours\n");
                output.append("Work: ").append(act.getWork()).append(" hours\n");
                output.append("Leisure: ").append(act.getLeisure()).append(" hours");

                SpecialOutputs.setText(output.toString());
                return;
            }
        }

        SpecialOutputs.setText("No data logged for " + capitalize(day) + ".");
    }



    @FXML
    private void handleMaxandMinActivity() {
        String day = DayChoice.getValue();

        if (day == null || day.trim().isEmpty()) {
            status_label.setText("Please select a valid day.");
            return;
        }

        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                // Store all activities and their hours
                Map<String, Integer> activityMap = new HashMap<>();
                activityMap.put("Sleep", act.getSleep());
                activityMap.put("Exercise", act.getExercise());
                activityMap.put("Study", act.getStudy());
                activityMap.put("Work", act.getWork());
                activityMap.put("Leisure", act.getLeisure());

                // Find max and min
                String maxActivity = null, minActivity = null;
                int maxHours = Integer.MIN_VALUE, minHours = Integer.MAX_VALUE;

                for (Map.Entry<String, Integer> entry : activityMap.entrySet()) {
                    int hours = entry.getValue();
                    if (hours > maxHours) {
                        maxHours = hours;
                        maxActivity = entry.getKey();
                    }
                    if (hours < minHours) {
                        minHours = hours;
                        minActivity = entry.getKey();
                    }
                }

                // Show results in Special Output box
                StringBuilder output = new StringBuilder();
                output.append("Most & Least Time Spent on ").append(capitalize(day)).append(":\n");
                output.append("Most Time: ").append(maxActivity).append(" (").append(maxHours).append("h)\n");
                output.append("Least Time: ").append(minActivity).append(" (").append(minHours).append("h)");

                SpecialOutputs.setText(output.toString());
                return;
            }
        }

        SpecialOutputs.setText("No data logged for " + capitalize(day) + ".");
    }




}
