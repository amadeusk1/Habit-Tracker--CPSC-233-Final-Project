import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Activity class.
 */
public class ActivityTest {

    /**
     * Tests that the setter methods update all activity fields,
     * and that the corresponding getter methods return the updated values.
     */
    @Test
    public void testConstructorAndGetters() {
        Activity act = new Activity("monday", 7, 2, 4, 8, 3);

        assertEquals("monday", act.getDay());
        assertEquals(7, act.getSleep());
        assertEquals(2, act.getExercise());
        assertEquals(4, act.getStudy());
        assertEquals(8, act.getWork());
        assertEquals(3, act.getLeisure());
    }

    /**
     * Tests the toString method to verify that the string output
     * includes the correct day (capitalized) and all activity values.
     */
    @Test
    public void testSetters() {
        Activity act = new Activity("tuesday", 0, 0, 0, 0, 0);
        act.setSleep(6);
        act.setExercise(1);
        act.setStudy(3);
        act.setWork(9);
        act.setLeisure(5);

        assertEquals(6, act.getSleep());
        assertEquals(1, act.getExercise());
        assertEquals(3, act.getStudy());
        assertEquals(9, act.getWork());
        assertEquals(5, act.getLeisure());
    }

    @Test
    public void testToStringIncludesAllFields() {
        Activity act = new Activity("friday", 6, 1, 2, 8, 3);
        String output = act.toString();

        // Check that the output contains the formatted values.
        assertTrue(output.contains("Sleep: 6 hours"), "Expected output 'Sleep: 6 hours'");
        assertTrue(output.contains("Exercise: 1 hours"), "Expected output 'Exercise: 1 hours'");
        assertTrue(output.contains("Study: 2 hours"), "Expected output 'Study: 2 hours'");
        assertTrue(output.contains("Work: 8 hours"), "Expected output to 'Work: 8 hours'");
        assertTrue(output.contains("Leisure: 3 hours"), "Expected output 'Leisure: 3 hours'");
    }


    /**
     * Check if the getters work for Activity class
     */
    @Test
    public void ConstructorAndGettersTues() {
        Activity act = new Activity("Tuesday", 5, 1, 1, 1, 10);

        assertEquals("Tuesday", act.getDay());
        assertEquals(5, act.getSleep());
        assertEquals(1, act.getExercise());
        assertEquals(1, act.getStudy());
        assertEquals(1, act.getWork());
        assertEquals(10, act.getLeisure());
    }

    /**
     * checks if setters work
     */
    @Test
    public void SetterBlank() {
        Activity act = new Activity("tuesday", 0, 0, 0, 0, 0);
        act.setSleep(10);
        act.setExercise(0);
        act.setStudy(0);
        act.setWork(0);
        act.setLeisure(10);

        assertEquals(10, act.getSleep());
        assertEquals(0, act.getExercise());
        assertEquals(0, act.getStudy());
        assertEquals(0, act.getWork());
        assertEquals(10, act.getLeisure());
    }
}