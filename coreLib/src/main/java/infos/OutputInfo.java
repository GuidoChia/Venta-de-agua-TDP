package infos;

import java.util.Date;

/**
 * Interface that represents the info that can be read from an excel file.
 *
 * @author Guido Chia
 */
public interface OutputInfo {
    /**
     * Last buy date getter
     *
     * @return The last date the customer bought
     */
    Date getLastDate();

    /**
     * Money balance getter
     *
     * @return The balance of the customer
     */
    double getBalance();

    /**
     * Twenty canisters balance getter
     *
     * @return The balance of twenty canisters
     */
    int getTwentyBalance();

    /**
     * Twelve canisters balance getter
     *
     * @return The balance of twenty canisters
     */
    int getTwelveBalance();

    /**
     * Total canisters balance getter
     *
     * @return The total balance of canisters
     */
    int getCanistersBalance();

}
