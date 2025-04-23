package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.test; /**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileSaver;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileLoader;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * This class contains unit tests for the FileLoader class,
 * which is responsible for loading saved user data from a file.
 */
public class FileLoaderTest {

    /** The data object that holds user activity and goals during testing. */
    private Data data;

    /** The file used for testing file loading. */
    private File testFile;

    /**
     * Sets up a test file with sample data before each test.
     * Adds one day of activity data and goals, and saves it using FileSaver.
     */
    @BeforeEach
    public void setUp() {
        data = new Data();
        testFile = new File("testSaveFile.csv"); // File used for testing

        // Add sample goals and one activity
        Goals goals = new Goals(8, 2, 4, 6, 4);
        data.setGoals(goals);
        data.storeNewDay("monday", 8, 2, 4, 6, 4);

        // Save the data for loading test
        FileSaver.save(testFile, data);
    }

    /**
     * Tests loading from a valid file with saved data.
     * Verifies that data loads correctly and contains expected activities.
     */
    @Test
    public void testLoadValidFile() {
        Data loadedData = FileLoader.load(testFile);

        // Ensure data was loaded
        assertNotNull(loadedData, "Loaded data should not be null.");

        // Ensure one activity entry was loaded
        assertEquals(1, loadedData.getDays().size(), "There should be 1 activity in the loaded data.");
    }

    /**
     * Tests the behavior of loading from an invalid or non-existent file.
     * Expects FileLoader to return null when file can't be found or parsed.
     */
    @Test
    public void testLoadInvalidFile() {
        File invalidFile = new File("invalidFile.csv");

        // Attempt to load from a non-existent file
        Data loadedData = FileLoader.load(invalidFile);
        assertNull(loadedData, "Data loading from an invalid file should return null.");
    }

    /**
     * Tests that the loaded file contains the expected data structure.
     * Confirms that activity records are properly retrieved.
     */
    @Test
    public void testLoadFileContents() {
        Data loadedData = FileLoader.load(testFile);

        // Check that activities are present after load
        assertNotNull(loadedData.getDays(), "Loaded data should contain activities.");
    }
}