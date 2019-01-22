package visitors;

import customer.Customer;

public class PaidVisitor implements VisitorDouble {
    private double res;

    @Override
    public void visit(Customer c) {
        res +=c.getPaid();
    }

    @Override
    public double getResult() {
        return res;
    }
}
