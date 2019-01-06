package infos;

import java.util.Date;

/**
 * The interface that represents the info of a new buy
 * @author Guido Chia
 */
public interface BuyInfo {
    /**
     * Paid getter.
     * @return Amount of money paid
     */
    double getPaid();

    /**
     * Twenty canister getter
     * @return Amount of twenty canisters bought
     */
    int getTwentyCanister();

    /**
     * Twelve canister getter
     * @return Amount of twelve canisters bought
     */
    int getTwelveCanister();

    /**
     * Returned twenty canisters getter
     * @return Amount of returned twenty canisters
     */
    int getTwentyReturnedCanister();

    /**
     * Returned twelve canisters getter
     * @return Amount of returned twelve canisters
     */
    int getTwelveReturnedCanister();

    /**
     * Date getter
     * @return Date of the buy
     */
    Date getDate();

    /**
     * Name getter
     * @return Name of the customer
     */
    String getName();

}
