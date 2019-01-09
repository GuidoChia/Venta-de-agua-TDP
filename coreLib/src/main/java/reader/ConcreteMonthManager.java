package reader;

import java.util.Collection;

import customer.Customer;
import visitors.PaidVisitor;
import visitors.TwelveCanistersVisitor;
import visitors.TwentyCanistersVisitor;
import visitors.Visitor;

public class ConcreteMonthManager implements MonthManager {
    @Override
    public double getPaid(Collection<Customer> customers) {
        Visitor v = new PaidVisitor();
        visitAll(customers, v);

        return v.getResult();
    }

    @Override
    public int getTwentyBought(Collection<Customer> customers) {
        Visitor v = new TwentyCanistersVisitor();
        visitAll(customers, v);

        return (int) v.getResult();
    }

    @Override
    public int getTwelveBought(Collection<Customer> customers) {
        Visitor v = new TwelveCanistersVisitor();
        visitAll(customers, v);

        return (int) v.getResult();
    }

    private void visitAll(Collection<Customer> customers, Visitor v) {
        for (Customer c : customers) {
            c.accept(v);
        }
    }
}
