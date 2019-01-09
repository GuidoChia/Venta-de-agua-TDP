package reader;

import java.util.Collection;

import customer.Customer;

public interface MonthManager {

    /**
     * Gets the total paid money from the collection of customers
     * @return Amount of money paid
     */
    double getPaid(Collection<Customer> customers);

    /**
     * Gets the total twenty canisters bought from the collection of customers
     * @return Amount of twenty canisters bought
     */
    int getTwentyBought(Collection<Customer> customers);

    /**
     * Gets the total twenty canisters bought from the collection of customers
     * @return Amount of twenty canisters bought
     */
    int getTwelveBought(Collection<Customer> customers);
}
