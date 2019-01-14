package visitors;

import customer.Customer;

public interface VisitorDouble {
    /**
     * Visits the given customer
     * @param c The customer to be visited
     */
    void visit(Customer c);

    /**
     * Gets the result that the visitor contains
     * @return The result.
     */
    double getResult();
}
