package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

/**
 * CPSC 233 (Tut-05) Project: Habit Tracker
 * ------------------------------------------------
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 *
 */


/**
 * class to start the code running process without UML
 */
public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        Menu Menu = new Menu(data);
        System.out.println("WELCOME TO THE DAILY HABIT TRACKER!\n");
        // Ask the user if they want to use saved data (menuUseSaved will be called)
        if (Menu.menuUseSaved()) {
            // If true, load the saved data and proceed to the menu loop
            Menu.menuLoop();
        } else {
            // If false, set goals and log the first activity, then proceed to the menu loop
            Menu.menuGoalSet();
            Menu.menuLogFirstActivity();
            Menu.menuLoop();
        }
    }
}
