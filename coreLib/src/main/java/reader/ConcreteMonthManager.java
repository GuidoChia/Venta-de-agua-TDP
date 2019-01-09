package reader;

import java.util.Collection;

import customer.Customer;
import visitors.PaidVisitor;
import visitors.TwelveCanistersVisitor;
import visitors.TwentyCanistersVisitor;
import visitors.Visitor;

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
        Visitor v = new PaidVisitor();
        visitAll(v);

        return v.getResult();
    }

    @Override
    public int getTwentyBought() {
        Visitor v = new TwentyCanistersVisitor();
        visitAll(v);

        return (int) v.getResult();
    }

    @Override
    public int getTwelveBought() {
        Visitor v = new TwelveCanistersVisitor();
        visitAll( v);

        return (int) v.getResult();
    }

    /**
     * Visits all the customers with the visitor v
     * @param v Visitor
     */
    private void visitAll(Visitor v) {
        for (Customer c : customers) {
            c.accept(v);
        }
    }
}
