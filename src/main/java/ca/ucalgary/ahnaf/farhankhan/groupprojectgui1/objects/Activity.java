package ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects;

/**
 * @author  Dominik Trzesicki, Ahnaf Farhan Khan, Amadeus Kaczmarek
 * @email dominik.trzesicki@ucalgary.ca, ahnaf.farhankhan@ucalgary.ca, amadeus.kaczmarek@ucalgary.ca
 * @date 27 March 2025
 * @tutorial 05
 */


import java.awt.*;
import java.util.IllformedLocaleException;

/**
 * The Activity class represents daily activities for a specific day,
 * including time spent on sleep, exercise, study, work, and leisure.
 * It extends the Day class.
 */
public class Activity extends Day implements Comparable<Activity> {
    private int sleep, exercise, study, work, leisure;

    /**
     * Constructs a new Activity object with specified time allocations for a given day.
     *
     * @param day      The name of the day.
     * @param sleep    The time spent on sleep.
     * @param exercise The time spent on exercise.
     * @param study    The time spent on study.
     * @param work     The time spent on work.
     * @param leisure  The time spent on leisure.
     */
    public Activity(String day, int sleep, int exercise, int study, int work, int leisure){
        super(day);
        this.sleep = sleep;
        this.exercise = exercise;
        this.study = study;
        this.work = work;
        this.leisure = leisure;
    }

    /**
     * Returns a string representation of the Activity object
     * @return A string representation of the Activity object.
     */
    @Override
    public String toString() {
        return  super.toString() + "\n" +
                "\tSleep: " + sleep + " hours\n" +
                "\tExercise: " + exercise + " hours\n" +
                "\tStudy: " + study + " hours\n" +
                "\tWork: " + work + " hours\n" +
                "\tLeisure: " + leisure + " hours\n\n";
    }

    /** @return the time spent on sleep */
    public int getSleep() {
        return sleep;
    }

    /** @return the time spent on exercise */
    public int getExercise() {
        return exercise;
    }

    /** @return the time spent on study */
    public int getStudy() {
        return study;
    }

    /** @return the time spent on leisure */
    public int getLeisure() {
        return leisure;
    }

    /** @return the time spent on work */
    public int getWork() {
        return work;
    }

    /** Sets the time spent on sleep */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    /** Sets the time spent on exercise */
    public void setExercise(int exercise) {
        this.exercise = exercise;
    }

    /** Sets the time spent on study */
    public void setStudy(int study) {
        this.study = study;
    }

    /** Sets the time spent on work */
    public void setWork(int work) {
        this.work = work;
    }

    /** Sets the time spent on leisure */
    public void setLeisure(int leisure) {
        this.leisure = leisure;
    }

    /**
     * Compares this Activity with another based on the total hours spent on activities (sleep, exercise, study, work, leisure).
     * @param otherActivity The other Activity to compare to.
     * @return A negative integer if this activity's total hours are less than the other activity's total hours,
     *         zero if they are equal, or a positive integer if this activity's total hours are greater.
     */
    @Override
    public int compareTo(Activity otherActivity) {
        // Calculate total hours for both activities
        int totalHoursThis = this.sleep + this.exercise + this.study + this.work + this.leisure;
        int totalHoursOther = otherActivity.sleep + otherActivity.exercise + otherActivity.study + otherActivity.work + otherActivity.leisure;

        // Compare the total hours
        return Integer.compare(totalHoursThis, totalHoursOther);
    }
}
