package strategies;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class YearStrategy implements DateStrategy {
    private Calendar[] calendarCriteria;

    /**
     * Creates a new strategy with the given criteria date.
     * It will be checked if the date passed in belongsDate has the same year
     * than this one.
     *
     * @param criteriaDates The criteria dates
     */
    public YearStrategy(Date[] criteriaDates) {
        calendarCriteria = new Calendar[criteriaDates.length];
        for (int i = 0; i < criteriaDates.length; i++) {
            calendarCriteria[i] = Calendar.getInstance();
            calendarCriteria[i].setTime(criteriaDates[i]);
        }
    }

    @Override
    public boolean belongsDate(Date date) {
        Calendar calendarToCheck = Calendar.getInstance();
        calendarToCheck.setTime(date);

        boolean finish = false;

        for (int i = 0; (i < calendarCriteria.length) && !finish; i++) {
            finish = (calendarToCheck.get(YEAR) == calendarCriteria[i].get(YEAR));
        }
        return finish;
    }
}
