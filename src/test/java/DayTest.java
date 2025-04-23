import ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects.Day;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DayTest {

    //A dummy subclass of Day for testing purposes (since Day is abstract)
    static class DummyDay extends Day {
        //constructor that passes the day string to the superclass (Day)
        public DummyDay(String day) {
            super(day);
        }
    }

    /**
     * Test to check the behavior of getDay() and toString()
     */
    @Test
    public void testGetDayAndToString() {
        DummyDay day = new DummyDay("saturday");
        assertEquals("saturday", day.getDay());
        assertEquals("Saturday: ", day.toString());
    }

    /**
     * test to check if day object gets created and getDay works
     */
    @Test
    public void testGetDay() {
        Day monday = new Day("monday") {};
        assertEquals("monday", monday.getDay());
    }

    /**
     * Check is the day gets set properly
     */
    @Test
    public void testToString() {
        Day monday = new Day("monday") {};
        assertEquals("Monday: ", monday.toString());
    }

    /**
     * check to see if capitalizing the day for ToString function works.
     */
    @Test
    public void testCapitalize() {
        Day monday = new Day("monday") {};
        // Using reflection to access the private method
        try {
            java.lang.reflect.Method method = Day.class.getDeclaredMethod("capitalize", String.class);
            method.setAccessible(true);
            String result = (String) method.invoke(monday, "monday");
            assertEquals("Monday", result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }
}