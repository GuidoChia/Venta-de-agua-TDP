package customer;

import java.util.Date;
import java.util.List;

import visitors.VisitorCollection;
import visitors.VisitorDouble;

/**
 * Interface that represents a Customer
 * @author Guido Chia
 */
public interface Customer {
    /**
     * Gets the last dates the customer bought canisters.
     * @return Collection containing the last dates the customer bought canisters.
     */
    List<Date> getLastDates();

    /**
     * Gets the balance of the customer
     * @return The balance of the customer
     */
    double getBalance();

    /**
     * Gets the amount of money the customer paid
     * @return The amount of money the customer paid
     */
    double getPaid();

    /**
     * Gets the amount of twenty canisters bought in the given
     * @return Amount of twenty canisters bought
     */
    int getTwentyBought();
    /**
     * Gets the amount of twelve canisters bought in the given
     * @return Amount of twelve canisters bought
     */
    int getTwelveBought();

    /**
     * Sets the balance of the customer
     * @param balance Balance to set
     */
    void setBalance(double balance);

    /**
     * Adds the given amount of twelve and twenty canisters, and the money paid, and the given date.
     * @param date the date of the buy
     * @param twelveAmount Amount of twelve canisters
     * @param twentyAmount Amount of twenty canisters
     * @param paid Amount of money paid
     */
    void addUpdate(Date date, int twelveAmount, int twentyAmount, double paid);

    /**
     * Checks if the customer is empty.
     * A Customer is empty if and only if the balances are 0, and there are not lastDates, and
     * the paid method returns 0;
     * @return true if the customer is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Name getter
     * @return Name of the Customer
     */
    String getName();

    /**
     * Accepts the given  visitor
     * @param v VisitorDouble
     */
    void accept(VisitorDouble v);

    /**
     * Accepts the given  visitor
     * @param v VisitorCollection
     */
    void accept(VisitorCollection v);
}
