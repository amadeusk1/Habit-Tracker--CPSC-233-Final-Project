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
    private final List<Day> days;

    /**
     * Constructs a new Data object.
     */
    public Data() {
        this.days = new ArrayList<>();
    }


    /**
     * Stores or updates activity data for a day.

     * @param dayOfWeek the day of the week (e.g., "Monday")
     * @param sleep the number of sleep hours
     * @param exercise the number of exercise hours
     * @param study the number of study hours
     * @param work the number of work hours
     * @param leisure the number of leisure hours
     */
    public void storeNewDay(String dayOfWeek, int sleep, int exercise, int study, int work, int leisure) {
        for (Day day : days) {// Loop through each day
            if (day.getDay().equalsIgnoreCase(dayOfWeek)) {
                Activity existingDay = (Activity) day;
                // Update the stored values for this day
                existingDay.setSleep(sleep);
                existingDay.setExercise(exercise);
                existingDay.setStudy(study);
                existingDay.setWork(work);
                existingDay.setLeisure(leisure);
                // Exit the method
                return;
            }
        }
        // otherwise make a new day
        days.add(new Activity(dayOfWeek, sleep, exercise, study, work, leisure));
    }

    /**
     * Returns the full list of all logged days.
     */
    public List<Day> getDays() {
        return days;
    }

    /**
     * Sets the user’s daily goals for all tracked activities.
     */
    public void setGoals(Goals goals) {
        // Initialize the singleton Goals instance using the values from the provided Goals object.
        Goals.initialize(goals.getSleep(), goals.getExercise(), goals.getStudy(),
                goals.getWork(), goals.getLeisure());
    }

    /**
     * Retrieves the current Goals instance.
     *
     * @return the Goals instance from the Goals class.
     */
    public Goals getGoals() {
        return Goals.getInstance();
    }

    /**
     * Returns the activity data for a given day as a map.
     *
     * @param day the day for which to get the activity data (e.g., "Monday")
     * @return a map with keys "sleep", "exercise", "study", "work", and "leisure"
     *         and their corresponding hours, or null if the day wasn't found
     */
    public Map<String, Integer> getDayMap(String day) {
        for (Day d : days) { // Loop through each Day
            if (d.getDay().equalsIgnoreCase(day) && d instanceof Activity act) { // Check if the day matches
                Map<String, Integer> map = new HashMap<>();
                // Store each activity's hours in the  new map
                map.put("sleep", act.getSleep());
                map.put("exercise", act.getExercise());
                map.put("study", act.getStudy());
                map.put("work", act.getWork());
                map.put("leisure", act.getLeisure());
                return map;// return map
            }
        }
        return null; // If day not found
    }

    /**
     * Prints all stored activity data.
     */
    public void displayAllActivities() {
        // Print a header
        System.out.println("\n📅 Weekly Activities:");
        for (Day day : days) { // Loop through each day in the list and print its string representation
            System.out.println(day.toString());
        }
    }

    public String displayAllActivitiesGUI() {
        StringBuilder formated = new StringBuilder();
        for (Day day : days) { // Loop through each day in the list and add its string representation to a string
            formated.append(day.toString());
        }
        return formated.toString(); // return string
    }

    /**
     * used to clear days
     */
    public void clear() {
        days.clear();
    }


}
