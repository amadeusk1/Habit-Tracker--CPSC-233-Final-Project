package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * class used to load files
 */
public class FileLoader {

    /**
     * Loads data from a file and returns a filled Data object.
     *
     * @param file the file to load data from
     * @return a Data object with the loaded activities and goals, or null if the file format is not as expected or an error occurs
     */
    public static Data load(File file) {
        Data newData = new Data(); // Create a fresh data object to fill

        try (Scanner scanner = new Scanner(file)) {

            // First line must be "Activities"
            if (!scanner.hasNextLine()) return null;
            String section = scanner.nextLine();
            if (!section.equals("Activities")) return null;

            // Read all activities until "Goals" section is reached
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("Goals")) break; // Stop when we reach goals section

                String[] parts = line.split(",");
                if (parts.length != 6) continue; // Skip invalid lines (must have exactly 6 parts)

                try {
                    // Extract values from line
                    String day = parts[0];
                    int sleep = Integer.parseInt(parts[1]);
                    int exercise = Integer.parseInt(parts[2]);
                    int study = Integer.parseInt(parts[3]);
                    int work = Integer.parseInt(parts[4]);
                    int leisure = Integer.parseInt(parts[5]);

                    // Store the activity in the data object
                    newData.storeNewDay(day, sleep, exercise, study, work, leisure);
                } catch (NumberFormatException e) {
                    // Handle any invalid data in the file by skipping the line
                    System.out.println("Skipping invalid line: " + line);
                }
            }

            // Now read the goal values
            if (scanner.hasNextLine()) {
                String goalLine = scanner.nextLine();
                String[] goalParts = goalLine.split(",");
                if (goalParts.length == 5) {
                    try { // try to change into int
                        int gSleep = Integer.parseInt(goalParts[0]);
                        int gExercise = Integer.parseInt(goalParts[1]);
                        int gStudy = Integer.parseInt(goalParts[2]);
                        int gWork = Integer.parseInt(goalParts[3]);
                        int gLeisure = Integer.parseInt(goalParts[4]);

                        // Set the loaded goals in Data
                        Goals loadedGoals = new Goals(gSleep,gExercise,gStudy,gWork,gLeisure);
                        newData.setGoals(loadedGoals);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing goals from the file.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null; // Failed to read file
        }

        return newData; // Return the loaded data
    }
}