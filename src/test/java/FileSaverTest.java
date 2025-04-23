/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */

import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util.FileSaver;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.Data;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for the FileSaver class.
 * These tests verify that data can be correctly saved to a file,
 * errors are handled properly, and saved file contents behave as expected.
 */
public class FileSaverTest {

    private Data data;
    private File testFile;

    /**
     * Sets up fresh test data and file before each test runs.
     * Adds a sample day of activity and goals to the Data object.
     */
    @BeforeEach
    public void setUp() {
        // Set up test data and file for saving
        data = new Data();
        testFile = new File("testSaveFile.csv");

        // Sample Activity data
        Goals goals = new Goals(8, 2, 4, 6, 4); // Sample goals
        data.setGoals(goals);
        data.storeNewDay("monday", 8, 2, 4, 6, 4); // Adding sample activity for Monday
    }

    /**
     * Tests saving valid data to a file.
     * Verifies that the save method returns true, the file is created,
     * and it contains some content.
     */
    @Test
    public void testSaveValidFile() {
        // Sub-test 1: Save to file and check if it's successful
        boolean result = FileSaver.save(testFile, data);
        assertTrue(result, "Data should be saved successfully.");

        // Sub-test 2: Check if the file was created
        assertTrue(testFile.exists(), "Test file should exist after saving.");

        // Sub-test 3: Check if the file contains some content (basic check)
        assertTrue(testFile.length() > 0, "File should have content after saving.");
    }

    /**
     * Tests the behavior of saving data to invalid file paths.
     * Verifies that saving to read-only or non-existent directories fails.
     */
    @Test
    public void testSaveInvalidFile() {
        // Sub-test 1: Simulate saving data to a read-only file
        File invalidFile = new File("invalidDirectory/testSaveFile.csv");
        boolean result = FileSaver.save(invalidFile, data);
        assertFalse(result, "Data should fail to save to an invalid file.");

        // Sub-test 2: Try saving to a non-existent directory (handle with a new file path)
        File nonExistentDir = new File("nonexistentDirectory/testSaveFile.csv");
        boolean result2 = FileSaver.save(nonExistentDir, data);
        assertFalse(result2, "Data should fail to save to a non-existent directory.");
    }

    /**
     * Tests if file contents are saved correctly.
     * (Does not check exact contents for simplicity, but ensures file exists after save.)
     */
    @Test
    public void testSaveFileContents() {
        // Sub-test 1: Save data to file
        FileSaver.save(testFile, data);

        // Sub-test 2: Verify the file exists after saving
        assertTrue(testFile.exists(), "File should exist after save.");

        // Sub-test 3: (Optionally) Check if the file contents match the expected format by reading and comparing
        // You may use a FileReader to read back the file content and assert that it matches the expected format
        // Currently omitted for simplicity
    }

    /**
     * Tests if the FileSaver correctly overwrites an existing file when saving.
     */
    @Test
    public void testSaveFileOverwrite() {
        // Sub-test: Save data to an existing file and check if it overwrites correctly
        FileSaver.save(testFile, data);
        boolean result = FileSaver.save(testFile, data);
        assertTrue(result, "Data should overwrite the existing file.");
    }

    /**
     * Cleans up after each test by deleting the test file.
     */
    @AfterEach
    public void tearDown() {
        // Clean up after each test by deleting the test file
        if (testFile.exists()) {
            boolean deleted = testFile.delete();
            assertTrue(deleted, "Test file should be deleted after the test.");
        }
    }
}
