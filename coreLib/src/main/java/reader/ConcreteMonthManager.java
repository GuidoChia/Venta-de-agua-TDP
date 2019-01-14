package reader;

import java.util.Collection;

import customer.Customer;
import visitors.PaidVisitor;
import visitors.TwelveCanistersVisitor;
import visitors.TwentyCanistersVisitor;
import visitors.VisitorDouble;

public class ConcreteMonthManager implements MonthManager {

    private Collection<Customer> customers;

    /**
     * Creates a new ConcreteMonthManager with the given collection as the collection of
     * customers.
     * @param customers Collection of customers
     */
    public ConcreteMonthManager(Collection<Customer> customers){
        this.customers=customers;
    }
    @Override
    public double getPaid() {
        VisitorDouble v = new PaidVisitor();
        visitAll(v);

        return v.getResult();
    }

    @Override
    public int getTwentyBought() {
        VisitorDouble v = new TwentyCanistersVisitor();
        visitAll(v);

        return (int) v.getResult();
    }

    @Override
    public int getTwelveBought() {
        VisitorDouble v = new TwelveCanistersVisitor();
        visitAll( v);

        return (int) v.getResult();
    }

    /**
     * Visits all the customers with the visitor v
     * @param v VisitorDouble
     */
    private void visitAll(VisitorDouble v) {
        for (Customer c : customers) {
            c.accept(v);
        }
    }
}
