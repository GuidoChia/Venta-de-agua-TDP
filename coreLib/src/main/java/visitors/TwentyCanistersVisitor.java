package visitors;

import customer.Customer;

public class TwentyCanistersVisitor implements VisitorDouble {
    private double res=0;
    @Override
    public void visit(Customer c) {
        res+=c.getTwentyBought();
    }

    @Override
    public double getResult() {
        return res;
    }
}
