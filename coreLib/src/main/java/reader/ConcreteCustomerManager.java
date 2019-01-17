package reader;

import java.util.Collection;

import customer.Customer;
import visitors.PaidVisitor;
import visitors.RouteVisitor;
import visitors.TwelveCanistersVisitor;
import visitors.TwentyCanistersVisitor;
import visitors.VisitorCollection;
import visitors.VisitorDouble;

public class ConcreteCustomerManager implements CustomerManager {

    private Collection<Customer> customers;
    double cachedPaid, cachedTwelve, cachedTwenty;

    /**
     * Creates a new ConcreteCustomerManager with the given collection as the collection of
     * customers.
     *
     * @param customers Collection of customers
     */
    public ConcreteCustomerManager(Collection<Customer> customers) {
        this.customers = customers;
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
        if (cachedTwenty==0) {
            VisitorDouble v = new TwentyCanistersVisitor();
            visitAll(v);
            cachedTwenty=v.getResult();
        }
        return (int) cachedTwenty;
    }

    @Override
    public int getTwelveBought() {
        if (cachedTwelve==0) {
            VisitorDouble v = new TwelveCanistersVisitor();
            visitAll(v);
            cachedTwelve=v.getResult();
        }
        return (int) cachedTwelve;
    }

    @Override
    public Collection<Customer> getRoute() {
        VisitorCollection v = new RouteVisitor();
        visitAll(v);

        return v.getCollection();
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