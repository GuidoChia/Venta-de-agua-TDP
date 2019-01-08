package customer;

import java.util.Collection;
import java.util.Date;

/**
 * Interface that represents a Customer
 * @author Guido Chia
 */
public interface Customer {
    /**
     * Gets the last dates the customer bought canisters.
     * @return Collection containing the last dates the customer bought canisters.
     */
    Collection<Date> getLastBuys();

    /**
     * Gets the balance of the customer
     * @return The balance of the customer
     */
    double getBalance();

    /**
     * Gets the amount of money the customer paid in the given month
     * @param month The month to look in
     * @param year The year to look in
     * @return The amount of money the customer paid
     */
    double getMonthPaid(int month, int year);

    /**
     * Gets the amount of twenty canisters bought in the given month
     * @param month The month to look in
     * @param year The year to look in
     * @return Amount of twenty canisters bought
     */
    int getTwentyBought(int month, int year);
    /**
     * Gets the amount of twelve canisters bought in the given month
     * @param month The month to look in
     * @param year The year to look in
     * @return Amount of twelve canisters bought
     */
    int getTwelveBought(int month, int year);
}
