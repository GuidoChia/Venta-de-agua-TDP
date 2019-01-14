package visitors;

import java.time.Period;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import customer.Customer;

/**
 * Class implementing a visitor that accepts those customers that is probable will need
 * a canister soon
 */
public class RouteVisitor implements VisitorCollection {
    @Override
    public void visit(Customer c) {
        /*
        The customer will be added if the diference between today and the last buy
        is greater or equal to the average days from day to day.
         */
        Collection<Date> lastBuys = c.getLastDates();

        double currentDiference;

        Iterator<Date> it = lastBuys.iterator();



    }

    @Override
    public Collection<Customer> getCollection() {
        return null;
    }
}
