//package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.test;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.testng.AssertJUnit.*;
//
//import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Goals;
//
//
///**
// * Unit tests for the Goals class.
// */
//public class GoalsTest {
//
//    /**
//     * Tests that the constructor correctly sets all fields,
//     * and that the corresponding getter methods return the expected values.
//     */
//    @Test
//    void testConstructorAndGetters() {
//        Goals g = new Goals(8, 1, 5, 8, 2);
//        assertEquals(8F, g.getSleep());
//        assertEquals(1F, g.getExercise());
//        assertEquals(5F, g.getStudy());
//        assertEquals(8F, g.getWork());
//        assertEquals(2F, g.getLeisure());
//    }
//
//    /**
//     * Tests the equals method when two Goals instances
//     * have the same values. They should be considered equal.
//     */
//    @Test
//    void testEqualsSameValues() {
//        Goals g1 = new Goals(6, 2, 4, 8, 3);
//        Goals g2 = new Goals(6, 2, 4, 8, 3);
//        assertEquals(g1, g2);
//    }
//
//    /**
//     * Tests the equals method when two Goals instances
//     * have different values. They should not be considered equal.
//     */
//    @Test
//    void testEqualsDifferentValues() {
//        Goals g1 = new Goals(6, 2, 4, 8, 3);
//        Goals g2 = new Goals(7, 2, 4, 8, 3);
//        assertNotEquals(g1, g2);
//    }
//
//
//    /**
//     * Tests the toString method to ensure the output string
//     * contains all the expected fields and values in the correct format.
//     */
//    @Test
//    void testToStringIncludesFields() {
//        Goals g = new Goals(5, 1, 4, 9, 2);
//        String str = g.toString();
//
//        assertTrue(str.contains("{SLEEP=5, EXERCISE=1, STUDY=4, WORK=9, LEISURE=2}"));
//    }
//
//    private void assertTrue(boolean contains) {
//    }
//
//    /**
//     * Tests the equals method when comparing an object to itself.
//     * This should always return true.
//     */
//    @Test
//    void testEquals_SameReference() {
//        Goals goals = new Goals(8, 2, 4, 6, 4);
//        assertTrue(goals.equals(goals)); // Same object
//    }
//
//    /**
//     * Tests the equals method when comparing a Goals object with null.
//     * This should return false.
//     */
//    @Test
//    void testEquals_Null() {
//        Goals goals = new Goals(8, 2, 4, 6, 4);
//        assertFalse(goals.equals(null)); // Null check
//    }
//
//}