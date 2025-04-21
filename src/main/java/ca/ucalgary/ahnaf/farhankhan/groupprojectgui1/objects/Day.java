package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */


/** Day class represents a day of the week.
 * Has a child class of Activity
 */
public class Day{
    private final String day; // The name of the day

    /**
     * Constructs a new Day object with the specified day's name.
     *
     * @param day The name of the day.
     */
    public Day(String day) {
        this.day = day;
    }

    /**
     * Returns the name of the day.
     *
     * @return The name of the day.
     */
    public String getDay() {
        return day;
    }

    /**
     * Returns a string representation of the day, with the first letter capitalized.
     *
     * @return A string representation of the day.
     */
    @Override
    public String toString() {
        return capitalize(day) + ": ";
    }


    /**
     * Capitalizes the first letter of a string (e.g., "monday" → "Monday").
     *
     * @param input The string to be capitalized.
     * @return The capitalized string.
     */
    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
