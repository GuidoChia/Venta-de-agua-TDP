package visitors;

import customer.Customer;

public class TwentyCanistersVisitor implements VisitorDouble {
    private double res;
    @Override
    public void visit(Customer c) {
        res +=c.getTwentyBought();
    }

    @Override
    public double getResult() {
        return res;
    }
}
