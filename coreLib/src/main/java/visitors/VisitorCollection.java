package visitors;

import java.util.Collection;

import customer.Customer;

/**
 * Interface representing a visitor that returns a Collection of customers that match the
 * criteria implemented by the visit method.
 */
public interface VisitorCollection {
    /**
     * Visits the given customer
     *
     * @param c Customer to visit
     */
    void visit(Customer c);

    /**
     * Gets the collection result
     *
     * @return Collection with the customers that match the criteria
     */
    Collection<Customer> getCollection();
}
