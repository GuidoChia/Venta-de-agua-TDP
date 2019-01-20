package visitors;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import customer.Customer;

/**
 * Class implementing a visitor that accepts those customers that is probable will need
 * a canister soon
 */
public class RouteVisitor implements VisitorCollection {

    private Collection<Customer> res;

    public RouteVisitor() {
        res = new LinkedList<>();
    }

    @Override
    public void visit(Customer c) {
        /*
        The customer will be added if the diference between today and the last buy
        is greater or equal to the average days from day to day.
         */
        List<Date> lastBuys = c.getLastDates();

        double currentDifference;
        Date firstDate = lastBuys.get(0);
        int amount = lastBuys.size();
        Date lastDate = lastBuys.get(amount - 1);


        double averageDifference = getDateDiff(firstDate, lastDate, TimeUnit.MINUTES) / amount;

        currentDifference = getDateDiff(lastDate, Calendar.getInstance().getTime(), TimeUnit.MINUTES);

        if ((currentDifference >= averageDifference)&&averageDifference!=0) {
            res.add(c);
        }
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    @Override
    public Collection<Customer> getCollection() {
        return res;
    }
}
