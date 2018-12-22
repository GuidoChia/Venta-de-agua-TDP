package com.example.corelib;

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
    float getPaid();

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
     * Returned canisters getter
     * @return Amount of returned canisters
     */
    int getReturnedCanister();

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
