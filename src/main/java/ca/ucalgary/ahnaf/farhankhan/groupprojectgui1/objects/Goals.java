package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */



import java.util.EnumMap;
import java.util.Map;
/**
 * The Goals class represents a user's time allocation goals
 * across various categories such as sleep, exercise, study, work, and leisure.
 */
public class Goals {

    // Private static instance variable to hold the single global Goals object.
    private static Goals instance;

    private final Map<GoalType, Integer> goals; // Map to store time goals for different categories

    /**
     * Initializes the singleton Goals instance.
     * This creates or updates the current global Goals object.
     *
     * @param sleep    the sleep goal.
     * @param exercise the exercise goal.
     * @param study    the study goal.
     * @param work     the work goal.
     * @param leisure  the leisure goal.
     */
    public static void initialize(int sleep, int exercise, int study, int work, int leisure) {
        instance = new Goals(sleep, exercise, study, work, leisure);
    }

    /**
     * Constructs a new Goals object with specified time goals.
     *
     * @param sleep   the sleep goal
     * @param exercise the exercise goal
     * @param study   the study goal
     * @param work    the work goal
     * @param leisure the leisure goal
     */
    public Goals(int sleep, int exercise, int study, int work, int leisure) {
        goals = new EnumMap<>(GoalType.class);
        goals.put(GoalType.SLEEP, sleep);
        goals.put(GoalType.EXERCISE, exercise);
        goals.put(GoalType.STUDY, study);
        goals.put(GoalType.WORK, work);
        goals.put(GoalType.LEISURE, leisure);
    }

    /**
     * Returns the current global Goals instance.
     *
     * @return the current Goals object.
     * @throws IllegalStateException if the Goals instance has not been initialized.
     */
    public static Goals getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Goals have not been initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Checks if this Goals object is equal to another.
     *
     * @param o the other object
     * @return true if the goals match for all categories, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check reference equality
        if (!(o instanceof Goals)) return false; // Check type
        Goals g = (Goals) o;
        // Compare field values
        return goals.equals(g.goals);
    }

    /**
     * Returns a string representation of this Goals object.
     *
     * @return a string listing all goal values
     */
    @Override
    public String toString() {
        return "Sleep: " + getSleep() + " hours" +
                "\n\nExercise: " + getExercise() + " hours" +
                "\n\nStudy: " + getStudy() + " hours" +
                "\n\nWork: " + getWork() + " hours" +
                "\n\nLeisure: " + getLeisure() + " hours";
    }



    /** @return the sleep goal */
    public int getSleep() {
        return goals.getOrDefault(GoalType.SLEEP, 0);
    }

    /** @return the exercise goal */
    public int getExercise() {
        return goals.getOrDefault(GoalType.EXERCISE, 0);
    }

    /** @return the study goal */
    public int getStudy() {
        return goals.getOrDefault(GoalType.STUDY, 0);
    }

    /** @return the work goal */
    public int getWork() {
        return goals.getOrDefault(GoalType.WORK, 0);
    }

    /** @return the leisure goal */
    public int getLeisure() {
        return goals.getOrDefault(GoalType.LEISURE, 0);
    }

    /** Sets the goal for a specific type */
    public void setGoal(GoalType type, int value) {
        goals.put(type, value);
    }
}


