package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */


import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Day;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileLoader;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileSaver;

import javax.imageio.metadata.IIOMetadata;
import java.io.File;
import java.lang.constant.DynamicConstantDesc;
import java.util.*;


public class Menu {

    // The main data storage object
    private Data data;

    // Scanner for user input
    private final Scanner scanner;

    public Menu(Data data) {
        this.data = data;
        this.scanner = new Scanner(System.in);
    }

    /** Ask user if they want to use saved data
     * if they say yes then we call the load function and return true
     * if they say no then we return false and 'normal' tracker loop runs.
     *
     * @return
     */
    public boolean menuUseSaved() {
        // ask user question
        System.out.println("Do you want to use previously saved data?");
        System.out.println("'Y' for Yes. Anything else is No ");
        String choice = scanner.nextLine();
        if (choice.equals("Y")){ // if choice is Y
            if (load()) {// run load
                System.out.println("Press <Enter> to continue to the Menu.");
                scanner.nextLine();
                return true;
            }
        }
        // otherwise run normal program
        System.out.println("Press <Enter> to begin logging your habits.");
        scanner.nextLine();
        return false;
    }


    /**
     * Initial function that prompts user to enter his daily goals.
     * goals are measured in hours
     * function must be run to continue program.
     */
    // Function used to mandate the logging of the first activity
    public void menuGoalSet() {
        System.out.println("Please enter your daily goals for the following (in hours)");

        Goals newGoals; // To store the user's input as a Goals object
        int totalGoals; // Variable to store the total of the entered goals

        do {
            // Collect input for each goal activity
            int GoalSleep = checkInteger(scanner, "Sleep Goal: ");
            int GoalExercise = checkInteger(scanner, "Exercise Goal: ");
            int GoalStudy = checkInteger(scanner, "Study Goal: ");
            int GoalWork = checkInteger(scanner, "Work Goal: ");
            int GoalLeisure = checkInteger(scanner, "Leisure Goal: ");

            newGoals = new Goals(GoalSleep,GoalExercise,GoalStudy,GoalWork,GoalLeisure);

            // Sum the entered goals
            totalGoals = GoalSleep + GoalExercise + GoalStudy + GoalWork + GoalLeisure;

            // If total is not 24 hours, ask the user to re-enter the data
            if (totalGoals > 24) {
                System.out.println("The total of your goals must be less than or equal to 24 hours. Please try again.");
            } else {
                try {
                    // Compare with existing Goals if already set
                    Goals existingGoals = Goals.getInstance();
                    if (newGoals.equals(existingGoals)) {
                        System.out.println("You entered the same goals as before.");
                    } else {
                        data.setGoals(newGoals);
                        System.out.println("\n• Your daily goals have been set successfully!");
                    }
                } catch (IllegalStateException e) {
                    // No Goals instance exists yet, so initialize it
                    data.setGoals(newGoals);
                    System.out.println("\n• Your daily goals have been set successfully!");
                }
            }

        } while (totalGoals > 24); // Keep asking until the total is 24 hours
    }


    /**
     * Function used to mandate the logging of the first activity
     */
    public void menuLogFirstActivity() {
        // Initial prompts
        System.out.println("\nLets log your first activity.");
        System.out.println("Press <Enter> to continue.");
        scanner.nextLine();
        menuLogNewActivity();
    }

    // This is the array of put menu (has all the options the user can pick from)
    private static final ArrayList<String> options = new ArrayList<>();
    static {
        options.add("Exit"); // ends program
        options.add("Input more activities");                                              // used to input more than one activity
        options.add("Input new Goals");                                                    //used to input new goals
        options.add("View all logged activities");                                         // general output
        options.add("View all logged goals");                                               // view all the goals
        options.add("View logged activities on a specific day");                           // view logged activities on specific day
        options.add("Total hours logged");                                                 // view total logged hours
        options.add("Hours logged on a specific day");                                     // view hours logged on a specific day
        options.add("Activity with most / least amount of time spent on a specific day");  // view activity with the most and least amount of hours logged
        options.add("% of goals achieved in week");                                         // % of goals achieved during the week
        options.add("% of goals achieved on a day");                                        // % of goals achieved on a specific day.
        options.add("Number of goals achieved / not achieved during week");                // number of goals achieved / not achieved in the whole week.
        options.add("List time of activities logged over x hours");                         // the activities done that are over x hours
        options.add("Time remaining to achieve goals on a specific day");                   // the time remaining to achieve goals
        options.add("Load information");                                                    // loads the user data
        options.add("Save information");                                                    // saves the user data
    }

    // First display of the menu
    private static String message = """
           
           Menu Options
           
           Please select a option:
           """;
    // Appends all menu options to the message string
    static{
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        for (int i = 0; i < options.size(); i++){
            sb.append(String.format("\t%d) %s\n",i,options.get(i)));    // Formating adds a number to the start of each option
        }
        message = sb.toString();
    }

    /**
     * This loops the menu
     * after user pick the choice and get output then menu is shown again
     */
    public void menuLoop() {
        System.out.println(message); // prints initial message
        String choice = scanner.nextLine();
        int option;
        try {                                    // Try to convert the user input into an int (fails when blank or string was entered)
            option = Integer.parseInt(choice);
        }catch(NumberFormatException e){        // when fail then assign fake value to option
            option = 99; // fake value
        }

        // When option is not 0 then continue to function.
        while(option != 0){
            if(option > 0 && option < options.size()){
                System.out.printf("User Selected %d) %s%n", option,options.get(option));
                System.out.println("Press <Enter> to continue.");
                scanner.nextLine();
            }

            // Call the selected function
            switch (option) {
                case 1 -> menuLogNewActivity(); // Input more activities.
                case 2 -> menuGoalSet(); //Input new goals
                case 3 -> menuViewAllActivities(); // View all logged activities.
                case 4 -> menuViewAllGoals();  // view all logged goals
                case 5 -> menuViewActivityDay(); // View logged activities on a specific day.
                case 6 -> menuTotalHours(); // Total hours logged.
                case 7 -> menuHoursOnDay(); // Hours logged on a specific day.
                case 8 -> menuTimeActivity(); // core.objects.Activity with most / the least amount of time spent on a specific day.
                case 9 -> menuGoalsInWeek(); // % of goals achieved in week
                case 10 -> menuGoalsInDay(); // % of goals achieved in day
                case 11 -> menuNumberOfGoals(); //Number of goals achieved / not achieved during week.
                case 12 -> menuActivityOverGoals(); // how many activities reached ur goals
                case 13 -> menuTimeRemainingForGoals(); // how much time there is remaining to reach goals
                case 14 -> load(); // get the data from the file and save to program
                case 15 -> save(); // save the users data
                default -> System.out.printf("Option not recognized. %n");
            }

            // Prompts the user to see the menu if desired.
            System.out.println("Press <Enter> to see the menu.");
            scanner.nextLine();
            System.out.println(message); // prints initial message
            choice = scanner.nextLine();
            try { // try to convert the user input into an int (fails when blank or string was entered)
                option = Integer.parseInt(choice);
            }catch(NumberFormatException e){ // when fail then assign fake value to option
                option = 99; // fake value
            }

        }
        // if the user inputs 0, then end code
        System.out.println("Thanks for using the Daily Habit Tracker \n\nBye!");
        System.exit(0);
    }



    /**
     * Prompts the user for a filename and saves the current data to the specified file.
     * It uses the FileSaver class to write data to the file.
     * If the save operation is successful, a success message is displayed.
     * If the save operation fails, an error message is shown.
     */
    private void save() {
        String filename;
        File file;
        do {
            System.out.print("Enter a filename to save: \n");
            filename = scanner.nextLine().trim();
        } while (filename.isEmpty());

        file = new File(filename);

        // Save using FileSaver
        if (FileSaver.save(file, data)) {
            System.out.printf("Saved to file %s%n", filename);
        } else {
            System.out.printf("Failed to save file %s%n", filename);
        }
    }


    /**
     * Prompts the user for a filename and loads data from the specified file.
     * The method uses the FileLoader class to load data from the file.
     * If the load operation is successful, the data is updated and a success message is displayed.
     * If the load operation fails, an error message is shown, and the user is prompted to retry.
     *
     * @return true if data was successfully loaded, false if loading failed after multiple attempts
     */
    private boolean load() {
        String filename;
        File file;
        do {
            System.out.print("Enter a filename to load: \n");
            filename = scanner.nextLine().trim();
        } while (filename.isEmpty());

        file = new File(filename);

        // Load using FileLoader
        Data loadedData = FileLoader.load(file);
        if (loadedData != null) {
            data = loadedData;
            System.out.println("Data loaded successfully.");
            return true;
        } else {
            System.out.println("Failed to load data.");
            load();
            return false;
        }
    }





    /** Method to get a valid integer from the user
     *
     * @param scanner imports the scanner
     * @param prompt the question shown to user
     * @return the number if its was valid
     */
    public int checkInteger(Scanner scanner, String prompt) {
        int number;

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine(); // Changed to read input as a string

            try {
            number = Integer.parseInt(input);// Attempt to parse the input as an integer
            if (0 <= number && 24 >= number) { // limit numbers from 0 to 24 (hours in day)
                break;
            }else{ // catch invalid input when not from 0 to 24
                System.out.println("Invalid input. Must be in the range of 0 to 24 (inclusive)");
            }
            } catch(NumberFormatException e){ // Catch invalid input
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return number;
    }

    /** Function to Capitalize Days
     *
     * @param input the string being capitalised
     * @return the capitalised sting (first letter)
     */
    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Called when the user chooses to enter a new activity
     */
    // Called when the user chooses to enter a new activity
    public void menuLogNewActivity() {
        // Ask the user for a valid day
        String day = getDay();

        // Loop to ensure the sum of hours is equal to 24
        int totalHours;
        int[] dataH; // Array to store activity hours
        do {
            // Get user input for the time spent on each activity
            dataH = addDataToday(day);

            // Calculate the total hours entered
            totalHours = 0;
            for (int hours : dataH) {
                totalHours += hours;
            }

            // If total is not 24 hours, ask the user to re-enter the data
            if (totalHours > 24) {
                System.out.println("The total hours must be less than or equal to 24. Please try again.");
            }

        } while (totalHours > 24); // Keep asking until the total is 24 hours

        // Store the valid data after the user has entered correct hours
        data.storeNewDay(day, dataH[0], dataH[1], dataH[2], dataH[3], dataH[4]);
        System.out.println("Activity data stored for " + capitalize(day) + " successfully!");
    }



    /** New function to add activities and hours to the corresponding HashMap
     *
     * @param day the day of the week
     *
     */
    private int[] addDataToday(String day) {
        System.out.println("Enter hours spent for each activity on " + capitalize(day) + ":");

        // Define an array of activities
        String[] activities = {"Sleep", "Exercise", "Study", "Work", "Leisure"};
        int[] timeSpent = new int[5];
        // Loop through each activity
        {
            String activity = activities[0];  // Get the activity name
            int hours = checkInteger(scanner, activity + " hours: ");  // Ask user for input
            timeSpent[0] = hours;  // Store the input into
        }
        {
            String activity = activities[1];  // Get the activity name
            int hours = checkInteger(scanner, activity + " hours: ");  // Ask user for input
            timeSpent[1] = hours;  // Store the input into
        }
        {
            String activity = activities[2];  // Get the activity name
            int hours = checkInteger(scanner, activity + " hours: ");  // Ask user for input
            timeSpent[2] = hours;  // Store the input into
        }
        {
            String activity = activities[3];  // Get the activity name
            int hours = checkInteger(scanner, activity + " hours: ");  // Ask user for input
            timeSpent[3] = hours;  // Store the input into
        }
        {
            String activity = activities[4];  // Get the activity name
            int hours = checkInteger(scanner, activity + " hours: ");  // Ask user for input
            timeSpent[4] = hours;  // Store the input into
        }
        return timeSpent;
    }



    /** Function used to get the day of the week
     *
     * @return a string of the day of the week
     */
    public String getDay() {
        System.out.print("Day of the week (Sunday --> Saturday): \n");
        String dayOfWeek = scanner.nextLine().toLowerCase(); // Convert input to lowercase

        switch (dayOfWeek) {
            case "monday":
            case "tuesday":
            case "wednesday":
            case "thursday":
            case "friday":
            case "saturday":
            case "sunday":
                System.out.println("You entered " + capitalize(dayOfWeek) + ". \n" );
                return dayOfWeek; // Return valid input

            default:
                System.out.println("Invalid day entered. Must be a day of the week.");
                return getDay(); // Recursively call until valid input
        }
    }



    /**
     * GENERAL OUTPUT
     * if called then shows all the activities.
     */
    public void menuViewAllActivities() {
        data.displayAllActivities();
    }

    /**
     * Displays the current daily goals for all activities.
     */
    private void menuViewAllGoals() {
        System.out.println("\nCurrent Daily Goals:");
        System.out.println("Sleep: " + Goals.getInstance().getSleep() + " hours per day");
        System.out.println("Exercise: " + Goals.getInstance().getExercise() + " hours per day");
        System.out.println("Study: " + Goals.getInstance().getStudy() + " hours per day");
        System.out.println("Work: " + Goals.getInstance().getStudy() + " hours per day");
        System.out.println("Leisure: " + Goals.getInstance().getLeisure() + " hours per day");
    }

    /**
     * Used to view all activities done on a specific day.
     */
    private void menuViewActivityDay() {
        String day = getDay();
        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day)) {
                System.out.println("\nActivities for " + capitalize(day) + ":");
                System.out.println(d);
                return;
            }
        }
        System.out.println("No data logged for " + capitalize(day) + ".");
    }


    /**
     * Calculates and displays the total hours logged across all days.
     * Also provides a breakdown of hours per activity.
     */
    private void menuTotalHours() {

        //Stores total hours
        int total = 0, sleep = 0, exercise = 0, study = 0, work = 0, leisure = 0; //Store total hours and total hours per activity
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

        System.out.println("Total Hours logged: " + total); //print total

        //Prints out the all hours per activity
        System.out.println("Sleep: " + sleep);
        System.out.println("Exercise: " + exercise);
        System.out.println("Study: " + study);
        System.out.println("Work: " + work);
        System.out.println("Leisure: " + leisure);
    }

    /**
     * Displays the total hours logged for a specific day.
     * Source used:
     * -> <a href="https://www.geeksforgeeks.org/hashmap-entryset-method-in-java/">...</a>
     */
    private void menuHoursOnDay() {

        String day = getDay();     //Reusing getDay() method to ensure valid day input

        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                int total = act.getSleep() + act.getExercise() + act.getStudy() + act.getWork() + act.getLeisure();

                //Print total logged hours for the day
                System.out.println("\nTotal hours logged on " + capitalize(day) + ": " + total);

                //Print total logged hours for activities
                System.out.println("Sleep: " + act.getSleep());
                System.out.println("Exercise: " + act.getExercise());
                System.out.println("Study: " + act.getStudy());
                System.out.println("Work: " + act.getWork());
                System.out.println("Leisure: " + act.getLeisure());
                return;
            }
        }
        System.out.println("No data logged for " + capitalize(day) + "."); //if no hours logged at all for that certain day print message
    }

    /**
     * Displays the activity with the most and least logged hours on a given day.
     * Prompts the user for a day, then finds and prints the activities with max and min hours.
     * Sources:
     * -MIN_VALUE and MAX_VALUE <a href="https://www.geeksforgeeks.org/integer-max_value-and-integer-min_value-in-java-with-examples/">...</a>
     */
    /**
     * Displays the activity or activities that had the most and least hours logged on a given day.
     * Prompts the user for the day and prints the results.
     */
    private void menuTimeActivity() {
        String day = getDay();

        for (Day d : data.getDays()) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {

                // Map to store activity names and hours
                Map<String, Integer> activityMap = new HashMap<>();
                activityMap.put("Sleep", act.getSleep());
                activityMap.put("Exercise", act.getExercise());
                activityMap.put("Study", act.getStudy());
                activityMap.put("Work", act.getWork());
                activityMap.put("Leisure", act.getLeisure());

                // Find max and min hours
                int maxHours = Collections.max(activityMap.values());
                int minHours = Collections.min(activityMap.values());

                // Store all activities with max and min hours
                List<String> maxActivities = new ArrayList<>();
                List<String> minActivities = new ArrayList<>();

                for (Map.Entry<String, Integer> entry : activityMap.entrySet()) {
                    if (entry.getValue() == maxHours) {
                        maxActivities.add(entry.getKey());
                    }
                    if (entry.getValue() == minHours) {
                        minActivities.add(entry.getKey());
                    }
                }

                // Output results
                System.out.println("Activities with most time on " + capitalize(day) + " (" + maxHours + " hours):");
                System.out.println("→ " + String.join(", ", maxActivities));

                System.out.println("Activities with least time on " + capitalize(day) + " (" + minHours + " hours):");
                System.out.println("→ " + String.join(", ", minActivities));
                return;
            }
        }

        // If no data matched
        System.out.println("No data logged for " + capitalize(day) + ".");
    }


    /**
    * Calculates and displays the percentage of weekly goals achieved per activity.
    * will not work if user did not enter goal values beforehand
    */
    private void menuGoalsInWeek() {

        // Create initial total hours for each activity
        int totalSleep = 0, totalExercise = 0, totalStudy = 0, totalWork = 0, totalLeisure = 0;

        // Array of days of the week
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        // Loop through each day's data and sum up the total hours for each activity
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

        // Change daily goals to weekly goals by multiplying by 7
        int GoalSleep = Goals.getInstance().getSleep() * 7;
        int GoalExercise = Goals.getInstance().getExercise() * 7;
        int GoalStudy = Goals.getInstance().getStudy() * 7;
        int GoalWork = Goals.getInstance().getWork() * 7;
        int GoalLeisure = Goals.getInstance().getLeisure() * 7;

        // Calculate percentage completion for each activity
        double percentSleep = (totalSleep / (double) GoalSleep) * 100;
        double percentExercise = (totalExercise / (double) GoalExercise) * 100;
        double percentStudy = (totalStudy / (double) GoalStudy) * 100;
        double percentWork = (totalWork / (double) GoalWork) * 100;
        double percentLeisure = (totalLeisure / (double) GoalLeisure) * 100;

        // Cap values at 100%
        if (percentSleep > 100) percentSleep = 100;
        if (percentExercise > 100) percentExercise = 100;
        if (percentStudy > 100) percentStudy = 100;
        if (percentWork > 100) percentWork = 100;
        if (percentLeisure > 100) percentLeisure = 100;

        // Display results
        System.out.println("\nPercentage of Weekly Goals Achieved:");
        System.out.printf("Sleep: %.2f%%\n", percentSleep);
        System.out.printf("Exercise: %.2f%%\n", percentExercise);
        System.out.printf("Study: %.2f%%\n", percentStudy);
        System.out.printf("Work: %.2f%%\n", percentWork);
        System.out.printf("Leisure: %.2f%%\n", percentLeisure);
    }

    /**
     * Displays the percentage of daily goals achieved for a given day.
     * Retrieves logged activity hours, compares them to set goals,
     * calculates percentages, and prints the results.
     * Ensures percentages do not exceed 100%.
     */
    private void menuGoalsInDay() {

        // Get the day from the user
        String day = getDay();

        // Retrieve the activity data for the selected day
        Map<String, Integer> dayMap = data.getDayMap(day);

        // Retrieve logged hours for the selected day
        int totalSleep = dayMap.getOrDefault("sleep", 0);
        int totalExercise = dayMap.getOrDefault("exercise", 0);
        int totalStudy = dayMap.getOrDefault("study", 0);
        int totalWork = dayMap.getOrDefault("work", 0);
        int totalLeisure = dayMap.getOrDefault("leisure", 0);

        // Retrieve daily goal values
        int GoalSleep = Goals.getInstance().getSleep();
        int GoalExercise = Goals.getInstance().getExercise();
        int GoalStudy = Goals.getInstance().getStudy();
        int GoalWork = Goals.getInstance().getWork();
        int GoalLeisure = Goals.getInstance().getLeisure();

        // Calculate percentages
        double percentSleep = (totalSleep / (double) GoalSleep) * 100;
        double percentExercise = (totalExercise / (double) GoalExercise) * 100;
        double percentStudy = (totalStudy / (double) GoalStudy) * 100;
        double percentWork = (totalWork / (double) GoalWork) * 100;
        double percentLeisure = (totalLeisure / (double) GoalLeisure) * 100;

        // Cap values at 100%
        if (percentSleep > 100) percentSleep = 100;
        if (percentExercise > 100) percentExercise = 100;
        if (percentStudy > 100) percentStudy = 100;
        if (percentWork > 100) percentWork = 100;
        if (percentLeisure > 100) percentLeisure = 100;

        // Display results
        System.out.println("\nPercentage of Daily Goals Achieved for " + capitalize(day) + ":");
        System.out.printf("Sleep: %.2f%%\n", percentSleep);
        System.out.printf("Exercise: %.2f%%\n", percentExercise);
        System.out.printf("Study: %.2f%%\n", percentStudy);
        System.out.printf("Work: %.2f%%\n", percentWork);
        System.out.printf("Leisure: %.2f%%\n", percentLeisure);
    }

    private void menuNumberOfGoals() {

        // Goal categories
        String[] activities = {"sleep", "exercise", "study", "work", "leisure"};

        // Weekly goal totals
        int GoalSleep = Goals.getInstance().getSleep() * 7;
        int GoalExercise = Goals.getInstance().getExercise() * 7;
        int GoalStudy = Goals.getInstance().getStudy() * 7;
        int GoalWork = Goals.getInstance().getWork() * 7;
        int GoalLeisure = Goals.getInstance().getLeisure() * 7;

        // Store total logged hours for each activity
        Map<String, Integer> totalLogged = new HashMap<>();
        totalLogged.put("sleep", 0);
        totalLogged.put("exercise", 0);
        totalLogged.put("study", 0);
        totalLogged.put("work", 0);
        totalLogged.put("leisure", 0);

        // Days of the week
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        // Loop through days and add total logged hours for each activity over the week
        for (String day : days) {
            Map<String, Integer> dayMap = data.getDayMap(day);
            if (dayMap != null) {
                for (String activity : activities) {
                    totalLogged.put(activity, totalLogged.get(activity) + dayMap.getOrDefault(activity, 0));
                }
            }
        }

        // core.objects.Goals reference map
        Map<String, Integer> weeklyGoals = new HashMap<>();
        weeklyGoals.put("sleep", GoalSleep);
        weeklyGoals.put("exercise", GoalExercise);
        weeklyGoals.put("study", GoalStudy);
        weeklyGoals.put("work", GoalWork);
        weeklyGoals.put("leisure", GoalLeisure);

        // Used to count achieved and non achieved
        int goalsAchieved = 0;
        int goalsNotAchieved = 0;

        for (String activity : activities) {
            if (totalLogged.get(activity) >= weeklyGoals.get(activity)) {
                goalsAchieved++;
            } else {
                goalsNotAchieved++;
            }
        }

        // Display results
        System.out.println("\nNumber of core.objects.Goals Achieved / Not Achieved This Week:");
        System.out.println("Goals Achieved: " + goalsAchieved);
        System.out.println("Goals Not Achieved: " + goalsNotAchieved);

    }

    /**
     * Displays activities that exceeded the user's goals on a specific day.
     * Prompts the user for a day, retrieves the corresponding activity,
     * and prints only the categories that went over the set goals.
     */
    private void menuActivityOverGoals() {
        String day = getDay(); // Prompt for day input
        Activity act = getActivityForDay(day); //retrieve activity data for that day

        if (act != null) {
            System.out.println("\nActivities over goal on " + capitalize(day) + ":");
            compareActivityToGoal(act, true); //compare and show over-goal categories
        } else {
            System.out.println("No data logged for " + capitalize(day) + "."); //No data found
        }
    }

    /**
     * Displays the remaining time required to meet the user's goals for a given day.
     * Prompts for a day and shows how many hours are needed to reach each goal.
     */
    private void menuTimeRemainingForGoals() {
        String day = getDay(); //prompt for day input
        Activity act = getActivityForDay(day); //retrieve activity data for that day

        if (act != null) {
            System.out.println("\nTime remaining to achieve goals on " + capitalize(day) + ":");
            compareActivityToGoal(act, false); //compare and show remaining time
        } else {
            System.out.println("No data logged for " + capitalize(day) + "."); //no data found
        }
    }

    /**
     * Retrieves the Activity for a specific day, if one exists.
     *
     * @param day the name of the day to look up
     * @return the Activity object for the day, or null if not found
     */
    private Activity getActivityForDay(String day) {
        for (Day d : data.getDays()) {
            //check day match and type cast to Activity
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                return act;
            }
        }
        return null; //no match found
    }

    /**
     * Compares actual activity data against goal values, and displays either:
     * - Over-goal values (if overGoal is true), or
     * - Remaining time to goal (if overGoal is false).
     *
     * @param act      the activity data to compare
     * @param overGoal flag indicating type of comparison
     */
    private void compareActivityToGoal(Activity act, boolean overGoal) {
        // Categories to compare
        String[] labels = {"Sleep", "Exercise", "Study", "Work", "Leisure"};

        //actual activity values for the day
        int[] actuals = {
                act.getSleep(),
                act.getExercise(),
                act.getStudy(),
                act.getWork(),
                act.getLeisure()
        };

        //user-defined goals for each category
        int[] goals = {
                Goals.getInstance().getSleep(),
                Goals.getInstance().getExercise(),
                Goals.getInstance().getStudy(),
                Goals.getInstance().getWork(),
                Goals.getInstance().getLeisure()
        };

        for (int i = 0; i < labels.length; i++) {
            if (overGoal && actuals[i] > goals[i]) {
                //print only if the actual exceeds the goal
                System.out.println(labels[i] + ": " + actuals[i] + "h (Goal: " + goals[i] + "h)");
            } else if (!overGoal) {
                //print time remaining to hit the goal
                System.out.println(labels[i] + ": " + Math.max(0, goals[i] - actuals[i]) + "h");
            }
        }
    }

}

