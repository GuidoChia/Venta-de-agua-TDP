package strategies;

import java.util.Date;

/**
 * Interface that gives a Strategy to check if a Date belongs or not according to a criteria
 * The criteria is defined by the classes that implements this interface
 *
 * @author Guido Chia
 */
public interface DateStrategy {


    /**
     * Checks if the given date matches the criteria
     *
     * @param date Date to check
     * @return True if the date matches the criteria, false otherwise
     */
    boolean belongsDate(Date date);

    /**
     * Compares the given date with the criteria dates.
     *
     * @param date
     * @return true if date is smaller than all the dates, false otherwise
     */
    boolean isSmaller(Date date);
}
