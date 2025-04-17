package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * used to run application
 */
public class MainGUI extends Application {

    private static String startupFilePath = null;

    /**start of program java fx
     *
     * @param args argument passed
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            startupFilePath = args[0];
        }
        launch(args);
    }


    /**start of GUI
     *
     * @param stage the stage for app
     * @throws IOException just in case there is an error
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);
        stage.setTitle("Habit Tracker");
        stage.setScene(scene);

        // Inject file path into controller if present
        MainController controller = fxmlLoader.getController();
        if (startupFilePath != null) {
            controller.setStartupFile(new File(startupFilePath));
        }

        stage.show();
    }
}