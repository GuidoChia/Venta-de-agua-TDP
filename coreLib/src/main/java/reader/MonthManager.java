package reader;

public interface MonthManager {

    /**
     * Gets the total paid money from the collection of customers
     * @return Amount of money paid
     */
    double getPaid();

    /**
     * Gets the total twenty canisters bought from the collection of customers
     * @return Amount of twenty canisters bought
     */
    int getTwentyBought();

    /**
     * Gets the total twenty canisters bought from the collection of customers
     * @return Amount of twenty canisters bought
     */
    int getTwelveBought();
}
