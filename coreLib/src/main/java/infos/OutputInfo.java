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
     * @return The balance of twelve canisters
     */
    int getTwelveBalance();

    /**
     * Twenty canisters balance getter
     *
     * @return The amount of twenty canisters bought
     */
    int getTwentyBought();

    /**
     * Twelve canisters balance getter
     *
     * @return The amount of twelve canisters bought
     */
    int getTwelveBought();

    /**
     * Total canisters balance getter
     *
     * @return The total balance of canisters
     */
    int getCanistersBalance();

    /**
     * Description getter.
     *
     * @return Description of the buy, if there is one, empty string otherwise.
     */
    String getDescription();

}
