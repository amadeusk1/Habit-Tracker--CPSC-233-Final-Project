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

import java.util.*;


/**
 * This class manages the data storage for a Daily Habit Tracker.
 * It keeps track of user activities (e.g., sleep, exercise, study, work, leisure)
 * for each day of the week and allows setting and retrieving activity goals.
 */
public class Data {

    // List to store each day's activity data
    private static final List<Day> days = new ArrayList<>();

    // Object to store user-defined daily goals
    public static Goals goal;

    /**
     * Stores or updates an Activity entry for a specific day.
     * If the day already exists, it updates the existing entry.
     * Otherwise, adds a new Activity object.
     */
    public static void storeNewDay(String dayOfWeek, int sleep, int exercise, int study, int work, int leisure) {
        for (Day day : days) {
            if (day.getDay().equalsIgnoreCase(dayOfWeek)) {
                Activity existingDay = (Activity) day;
                existingDay.setSleep(sleep);
                existingDay.setExercise(exercise);
                existingDay.setStudy(study);
                existingDay.setWork(work);
                existingDay.setLeisure(leisure);
                return;
            }
        }
        days.add(new Activity(dayOfWeek, sleep, exercise, study, work, leisure));
    }

    /**
     * Returns the full list of all logged days.
     */
    public static List<Day> getDays() {
        return days;
    }

    /**
     * Sets the user’s daily goals for all tracked activities.
     */
    public static void setGoals(Goals goals) {
        Data.goal = goals;
    }


    /**
     * Converts the list of days into a map of activities per day.
     * Used for goal calculations across the week.
     */
    public static Map<String, Integer> getDayMap(String day) {
        for (Day d : days) {
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) {
                Map<String, Integer> map = new HashMap<>();
                map.put("sleep", act.getSleep());
                map.put("exercise", act.getExercise());
                map.put("study", act.getStudy());
                map.put("work", act.getWork());
                map.put("leisure", act.getLeisure());
                return map;
            }
        }
        return null; // If day not found
    }

    /**
     * Prints all stored activity data.
     */
    public static void displayAllActivities() {
        System.out.println("\n📅 Weekly Activities:");
        for (Day day : days) {
            System.out.println(day.toString());
        }
    }

    public static String displayAllActivitiesGUI() {
        StringBuilder formated = new StringBuilder();
        for (Day day : days) {
            formated.append(day.toString());
        }
        return formated.toString();
    }

}
