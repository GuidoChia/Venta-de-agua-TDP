package visitors;

import customer.Customer;

public class TwelveCanistersVisitor implements VisitorDouble {
    private double res;

    @Override
    public void visit(Customer c) {
        res += c.getTwelveBought();
    }

    @Override
    public double getResult() {
        return res;
    }
}
