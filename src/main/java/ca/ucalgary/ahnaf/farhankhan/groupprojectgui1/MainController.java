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

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 17 April 2025
 * @tutorial 05
 */
public class MainController {
    // made for storing data
    private Data data = new Data();

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

    private File startupFile;  //startup file

    public void setStartupFile(File file) {
        this.startupFile = file;
    }

    public void loadStartupFileIfPresent() {
        if (startupFile != null && startupFile.exists()) {
            load(startupFile);
            ActivityDisplay.setText(data.displayAllActivitiesGUI());
            Goals currentGoals = Goals.getInstance();
            if (currentGoals != null) {
                GoalsDisplay.setText(currentGoals.toString());
            }
        }
    }



    /** used for the about section in gui
     *
     * @param event on click
     */
    @FXML
    void AboutHT(ActionEvent event) {
        // make the alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // create the writing portions of it
        alert.setTitle("About Habit Tracker");
        alert.setHeaderText("Habit Tracker Info");
        // set the content
        alert.setContentText("""
                Authors: Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
                Email: dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
                Version: v1.0
                Description: This is a Habit Tracker for daily use, that will show the effectiveness of your goal setting.
                """);
        // display it
        alert.show();
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
    }


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
            this.data = data;
            ActivityDisplay.setText(data.displayAllActivitiesGUI());
            Goals currentGoals = Goals.getInstance();
            GoalsDisplay.setText(currentGoals.toString());
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

    /** initialize everything at the start of the program
     *
     */
    @FXML
    public void initialize() {
        // add the DayChoice menu
        DayChoice.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        // add the Goal / activity menu
        GoalActivityChoice.setItems(FXCollections.observableArrayList("Goals", "Activity"));

        // Set DayChoice to be hidden by default since initial selection is "Goal"
        DayChoice.setVisible(false);
        dayText.setVisible(false);

        // check for changes on GoalActivityChoice
        GoalActivityChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Show the DayChoice only if the user selects "Habit"
            if ("Activity".equals(newValue)) {
                DayChoice.setVisible(true);
                dayText.setVisible(true);
            } else {// otherwise keep hidden
                DayChoice.setVisible(false);
                dayText.setVisible(false);
            }
        });
    }

    /** logging new info
     *
     * @param event on the click
     */
    @FXML
    void confirmAction(ActionEvent event) {
        // Get the selected values from goals
        String selectedGoal = GoalActivityChoice.getValue();
        try { // try to parse as integers
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

            // if goals are selected
            if ("Goals".equals(selectedGoal)) {
                if (sumTotal <= 24) {
                    // Initialize the global Goals instance.
                    Goals.initialize(sleepV, exerciseV, studyV, workV, leisureV);
                    Goals currentGoals = Goals.getInstance(); // add the goals
                    GoalsDisplay.setText(currentGoals.toString()); // show the goals
                    status_label.setText("Goals logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }
                // if activity is selected
            } else if ("Activity".equals(selectedGoal)) {
                String selectedDay = DayChoice.getValue();
                if (selectedDay == null || selectedDay.trim().isEmpty()) {
                    status_label.setText("Please select a valid day for your activity.");
                    return;
                }
                // check is hours aren't over 24
                if (sumTotal <= 24) {
                    //add the activity
                    Activity activity = new Activity(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    data.storeNewDay(selectedDay, sleepV, exerciseV, studyV, workV, leisureV);
                    ActivityDisplay.setText(data.displayAllActivitiesGUI()); // display activity
                    status_label.setText("Activities logged successfully");
                } else {
                    status_label.setText("The total of your goals must be less than or equal to 24 hours. Please try again.");
                }

            } else { // if they didnt select
                status_label.setText("Select Goal / Activity");
            }
        // if inputs are not integers
        } catch (NumberFormatException e) {
            status_label.setText("Invalid input. Please enter a valid integer for all inputs .");
        }
    }


    @FXML
    private TextArea SpecialOutputs;

    /**
     * Handles the "Weekly Goals Achieved" button action.
     * Calculates the percentage of goals achieved for each activity type
     * over the past week and displays the results in a TextArea.
     * If no goals have been set, it displays a warning message.
     */
    @FXML
    private void handleWeeklyGoalsAchieved() {

        // Get current goals, show a message if not set
        Goals currentGoals;
        try {
            currentGoals = Goals.getInstance();
        } catch (IllegalStateException ex) {
            SpecialOutputs.setText("No goals have been set yet. Please set your goals before checking progress.");
            return;
        }

        // Set total counters for the week
        int totalSleep = 0, totalExercise = 0, totalStudy = 0, totalWork = 0, totalLeisure = 0;
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        // Add up values for each day
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

        // Calculate weekly goals
        int goalSleep = currentGoals.getSleep() * 7;
        int goalExercise = currentGoals.getExercise() * 7;
        int goalStudy = currentGoals.getStudy() * 7;
        int goalWork = currentGoals.getWork() * 7;
        int goalLeisure = currentGoals.getLeisure() * 7;

        // Initialize percentages
        double percentSleep = 0;
        double percentExercise = 0;
        double percentStudy = 0;
        double percentWork = 0;
        double percentLeisure = 0;

        // Calculate percent values (avoid divide by zero)
        if (goalSleep != 0) percentSleep = (totalSleep / (double) goalSleep) * 100;
        if (goalExercise != 0) percentExercise = (totalExercise / (double) goalExercise) * 100;
        if (goalStudy != 0) percentStudy = (totalStudy / (double) goalStudy) * 100;
        if (goalWork != 0) percentWork = (totalWork / (double) goalWork) * 100;
        if (goalLeisure != 0) percentLeisure = (totalLeisure / (double) goalLeisure) * 100;

        // Limit to 100%
        if (percentSleep > 100) percentSleep = 100;
        if (percentExercise > 100) percentExercise = 100;
        if (percentStudy > 100) percentStudy = 100;
        if (percentWork > 100) percentWork = 100;
        if (percentLeisure > 100) percentLeisure = 100;

        // Build the output message
        String output = "Percentage of Weekly Goals Achieved:\n" +
                String.format("Sleep: %.2f%%\n", percentSleep) +
                String.format("Exercise: %.2f%%\n", percentExercise) +
                String.format("Study: %.2f%%\n", percentStudy) +
                String.format("Work: %.2f%%\n", percentWork) +
                String.format("Leisure: %.2f%%\n", percentLeisure);

        // Show result in the TextArea
        SpecialOutputs.setText(output);
    }


    /**
     * Prompts the user to enter a day of the week using a text input dialog.
     * Keeps asking until a valid day (Monday–Sunday) is entered or the user cancels.
     *
     * @return the valid day in lowercase (e.g., "monday"), or an empty string if canceled
     */
    private String getDay() {
        while (true) {
            // Show input dialog
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Day");
            dialog.setHeaderText("Day of the week (Monday-Sunday):");
            dialog.setContentText("Day:");

            Optional<String> result = dialog.showAndWait();

            // Return empty string if canceled
            if (result.isEmpty()) {
                return "";
            }

            // Convert input to lowercase
            String dayOfWeek = result.get().toLowerCase();

            // Check if valid day
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
                    // Show error if invalid
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
        if (day.isEmpty()) return; // If user canceled or didn't enter anything

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

        // check if 0 entry and calculate percentage
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
        String result = "Percentage of Daily Goals Achieved for " + capitalize(day) + ":\n" +
                String.format("Sleep: %.2f%%\n", percentSleep) +
                String.format("Exercise: %.2f%%\n", percentExercise) +
                String.format("Study: %.2f%%\n", percentStudy) +
                String.format("Work: %.2f%%\n", percentWork) +
                String.format("Leisure: %.2f%%\n", percentLeisure);

        // Display in TextArea
        SpecialOutputs.setText(result);
    }

    @FXML
    private void handleNumberGoalsAchieved() {

        Goals currentGoals;
        try {
            currentGoals = Goals.getInstance();
        } catch (IllegalStateException e) {
            SpecialOutputs.setText("Goals have not been set. Please set your goals first.");
            return;
        }

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

        String result = "Number of Goals Achieved / Not Achieved This Week:\n" +
                "Goals Achieved: " + goalsAchieved + "\n" +
                "Goals Not Achieved: " + goalsNotAchieved;

        SpecialOutputs.setText(result);
    }

    @FXML
    private void handleGoalsExceeded() {
        String day = getDay(); // user input day
        Map<String, Integer> actMap = data.getDayMap(day); // returns Map<String, Integer>

        if (actMap != null) {
            String output = "Activities over goal on " + capitalize(day) + ":\n" +
                    getActivityOverGoalDetails(actMap);
            SpecialOutputs.setText(output);
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
        Map<String, Integer> actMap = data.getDayMap(day); // Fetch an activity map for the day

        if (actMap != null) {
            String output = "Time remaining to achieve goals on " + capitalize(day) + ":\n" +
                    getTimeRemainingDetails(actMap);
            SpecialOutputs.setText(output);
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



    /**
     * Calculates and displays the total hours spent across all days for each activity type.
     *
     * Sums up hours for sleep, exercise, study, work, and leisure from all logged days.
     * Displays the total and individual activity hours in the SpecialOutputs TextArea.
     *
     * This gives the user an overview of their total weekly activity distribution.
     */
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
        String output = "Total Hours Logged (All Days):\n" +
                "Total    : " + total    + " / 168 hours\n\n\n" +
                "Sleep    : " + sleep    + " hours\n\n" +
                "Exercise : " + exercise + " hours\n\n" +
                "Study    : " + study    + " hours\n\n" +
                "Work     : " + work     + " hours\n\n" +
                "Leisure  : " + leisure  + " hours";


        SpecialOutputs.setText(output);
    }


    /**
     * Prompts the user to enter a day, then shows the total hours logged for that specific day.
     *
     * Asks for a day using a popup dialog.
     * Find that day’s activity entry (if available).
     * Sums and displays all activity hours in the SpecialOutputs TextArea.
     *
     * Notifies the user if no data is logged for the entered day.
     */
    @FXML
    private void handleTotalHoursLogged_Day() {
        String day = getDay();

        if (day == null || day.trim().isEmpty()) {
            status_label.setText("Please select a valid day.");
            return;
        }

        day = day.toLowerCase(); // match backend format

        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                int total = act.getSleep() + act.getExercise() + act.getStudy() + act.getWork() + act.getLeisure();

                String output = "Total Hours Logged for " + capitalize(day) + ":\n" +
                        "Total    : " + total           + " / 24 hours\n\n\n" +
                        "Sleep    : " + act.getSleep()  + " hours\n\n" +
                        "Exercise : " + act.getExercise() + " hours\n\n" +
                        "Study    : " + act.getStudy()  + " hours\n\n" +
                        "Work     : " + act.getWork()   + " hours\n\n" +
                        "Leisure  : " + act.getLeisure() + " hours";

                SpecialOutputs.setText(output);
                return;
            }
        }

        SpecialOutputs.setText("No data logged for " + capitalize(day) + ".");
    }


    /**
     * Handles the "Max and Min Activity" action.
     *
     * This function checks which activities took the most and least amount of time
     * on a specific day entered by the user.
     * It shows the results in the SpecialOutputs TextArea.
     *
     * If the day is invalid or no data is found, it displays an appropriate message.
     */
    @FXML
    private void handleMaxAndMinActivity() {
        String day = getDay();

        // Check if the day is empty
        if (day == null || day.trim().isEmpty()) {
            status_label.setText("Please select a valid day.");
            return;
        }

        // Loop through all days
        for (Day d : data.getDays()) {
            // Check if it's the correct day and is an Activity
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity) {
                Activity act = (Activity) d;

                // Store activities and their times
                int sleep = act.getSleep();
                int exercise = act.getExercise();
                int study = act.getStudy();
                int work = act.getWork();
                int leisure = act.getLeisure();

                // Set initial max and min
                int max = sleep;
                int min = sleep;

                // Find max
                if (exercise > max) max = exercise;
                if (study > max) max = study;
                if (work > max) max = work;
                if (leisure > max) max = leisure;

                // Find min
                if (exercise < min) min = exercise;
                if (study < min) min = study;
                if (work < min) min = work;
                if (leisure < min) min = leisure;

                // Collect activities with max time
                String maxActivities = "";
                if (sleep == max) maxActivities += "Sleep, ";
                if (exercise == max) maxActivities += "Exercise, ";
                if (study == max) maxActivities += "Study, ";
                if (work == max) maxActivities += "Work, ";
                if (leisure == max) maxActivities += "Leisure, ";
                if (maxActivities.endsWith(", ")) {
                    maxActivities = maxActivities.substring(0, maxActivities.length() - 2);
                }

                // Collect activities with min time
                String minActivities = "";
                if (sleep == min) minActivities += "Sleep, ";
                if (exercise == min) minActivities += "Exercise, ";
                if (study == min) minActivities += "Study, ";
                if (work == min) minActivities += "Work, ";
                if (leisure == min) minActivities += "Leisure, ";
                if (minActivities.endsWith(", ")) {
                    minActivities = minActivities.substring(0, minActivities.length() - 2);
                }

                // Build output
                String output = "Most & Least Time Spent on " + capitalize(day) + ":\n\n\n" +
                        "Most Time  (" + max + "h)  : " + maxActivities + "\n\n" +
                        "Least Time  (" + min + "h)  : " + minActivities;

                SpecialOutputs.setText(output);
                return;
            }
        }

        // If no matching day found
        SpecialOutputs.setText("No data logged for " + capitalize(day) + ".");
    }


}