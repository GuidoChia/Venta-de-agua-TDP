package visitors;

import customer.Customer;

public class TwelveCanistersVisitor implements Visitor {
    private double res=0;

    @Override
    public void visit(Customer c) {
        res+=c.getTwelveBought();
    }

    @Override
    public double getResult() {
        return res;
    }
}
