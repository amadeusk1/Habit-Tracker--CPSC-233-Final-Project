package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Activity;

/**
 * Unit tests for the Activity class.
 */
public class ActivityTest {

    /**
     * Tests that the setter methods update all activity fields,
     * and that the corresponding getter methods return the updated values.
     */
    @Test
    void testConstructorAndGetters() {
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
    void testSetters() {
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
    void testToStringIncludesAllFields() {
        Activity act = new Activity("friday", 6, 1, 2, 8, 3);
        String output = act.toString();

        assertTrue(output.contains("Friday:"));
        assertTrue(output.contains("sleep = 6"));
        assertTrue(output.contains("exercise = 1"));
        assertTrue(output.contains("study = 2"));
        assertTrue(output.contains("work = 8"));
        assertTrue(output.contains("leisure = 3"));
    }

    /**
     * Check if the getters work for Activity class
     */
    @Test
    void ConstructorAndGettersTues() {
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
    void SetterBlank() {
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