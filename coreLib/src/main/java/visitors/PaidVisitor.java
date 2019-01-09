package visitors;

import customer.Customer;

public class PaidVisitor implements Visitor {
    private double res=0;

    @Override
    public void visit(Customer c) {
        res+=c.getMonthPaid();
    }

    @Override
    public double getResult() {
        return res;
    }
}
