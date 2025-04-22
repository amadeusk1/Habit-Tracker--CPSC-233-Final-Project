package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.PaddingLayout;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 17 April 2025
 * @tutorial 05
 */

/**
 * class to run the application of with the GUI
 */
public class MainGUI extends Application {

    private static String startupFilePath = null;

    /**launches the java fx
     *
     * @param args - command line arguments; if provided, the first one is used as startupFilePath
     */
    public static void main(String[] args) {
        // when there is argument
        if (args.length > 0) {
            startupFilePath = args[0];
        }
        launch(args);
    }


    /** launches the program
     *
     * @param stage the running stage
     * @throws IOException if there is an error
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("Main.fxml"));
        // create scene
        Scene scene = new Scene(fxmlLoader.load(), 900, 620);
        // set title
        stage.setTitle("Habit Tracker");
        // add stage and scene
        stage.setScene(scene);

        // Inject a file path into the controller if present
        MainController controller = fxmlLoader.getController();
        if (startupFilePath != null) {
            controller.setStartupFile(new File(startupFilePath));
            controller.loadStartupFileIfPresent();
        }
        // show the application
        stage.show();
    }
}