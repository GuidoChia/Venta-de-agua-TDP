package reader;

import java.util.Collection;
import java.util.LinkedList;

import customer.Customer;
import visitors.PaidVisitor;
import visitors.RouteVisitor;
import visitors.TwelveCanistersVisitor;
import visitors.TwentyCanistersVisitor;
import visitors.VisitorCollection;
import visitors.VisitorDouble;

public class ConcreteCustomerManager implements CustomerManager {

    private Collection<Customer> customers;
    private double cachedPaid;
    private double cachedTwelve;
    private double cachedTwenty;

    /**
     * Creates a new ConcreteCustomerManager with the given collection as the collection of
     * customers.
     *
     * @param customers Collection of customers
     */
    public ConcreteCustomerManager(Collection<Customer> customers) {
        if (customers != null) {
            this.customers = customers;
        } else {
            customers=new LinkedList<>();
        }
    }

    @Override
    public double getPaid() {
        if (cachedPaid == 0) {
            VisitorDouble v = new PaidVisitor();
            visitAll(v);
            cachedPaid = v.getResult();
        }

        return cachedPaid;
    }

    @Override
    public int getTwentyBought() {
        if (cachedTwenty == 0) {
            VisitorDouble v = new TwentyCanistersVisitor();
            visitAll(v);
            cachedTwenty = v.getResult();
        }
        return (int) cachedTwenty;
    }

    @Override
    public int getTwelveBought() {
        if (cachedTwelve == 0) {
            VisitorDouble v = new TwelveCanistersVisitor();
            visitAll(v);
            cachedTwelve = v.getResult();
        }
        return (int) cachedTwelve;
    }

    @Override
    public Collection<Customer> getRoute() {
        VisitorCollection v = new RouteVisitor();
        visitAll(v);

        return v.getCollection();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Customer c : customers) {
            builder.append(c.toString());
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    /**
     * Visits all the customers with the visitor v
     *
     * @param v VisitorDouble
     */
    private void visitAll(VisitorDouble v) {
        for (Customer c : customers) {
            c.accept(v);
        }
    }

    /**
     * Visits all the customers with the visitor v
     *
     * @param v VisitorCollection
     */
    private void visitAll(VisitorCollection v) {
        for (Customer c : customers) {
            c.accept(v);
        }
    }
}
