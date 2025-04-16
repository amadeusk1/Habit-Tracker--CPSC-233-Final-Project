package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util;

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Day;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The FileSaver class handles saving activity data and user goals to a file.
 */
public class FileSaver {

    /**
     * Saves the user's activity data and goal settings into the specified file.
     *
     * @param file the file to save data into
     * @param data the data object containing daily activities
     * @return true if saving was successful, false if an error occurred
     */
    public static boolean save(File file, Data data) {
        try (FileWriter fw = new FileWriter(file)) {

            // Write header for the activity section
            fw.write("Activities\n");

            // Write each activity in the format: day,sleep,exercise,study,work,leisure
            for (Day d : data.getDays()) {
                if (d instanceof Activity act) {
                    fw.write(String.format("%s,%d,%d,%d,%d,%d\n",
                            act.getDay(),
                            act.getSleep(),
                            act.getExercise(),
                            act.getStudy(),
                            act.getWork(),
                            act.getLeisure()));
                }
            }

            // Write header for the goals section
            fw.write("Goals\n");

            // Write the goal values: sleep, exercise, study, work, leisure
            Goals goal = Goals.getInstance();
            fw.write(String.format("%d,%d,%d,%d,%d\n",
                    goal.getSleep(),
                    goal.getExercise(),
                    goal.getStudy(),
                    goal.getWork(),
                    goal.getLeisure()));

            fw.flush(); // Ensure all data is written to file
            return true; // Save successful

        } catch (IOException e) {
            return false; // Something went wrong during saving
        }
    }
}
