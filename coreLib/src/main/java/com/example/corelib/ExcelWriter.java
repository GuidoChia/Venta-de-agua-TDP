package com.example.corelib;

/**
 * The interface that represents a writer of BuyInfo in an excel file.
 * The file is assumed to have the next format:
 * First column: Date of buy.
 * Second column: amount of twenty canisters bought.
 * Third column: amount of twelve canisters bought.
 * Forth column: total price of the buy.
 * Fifth column: total amount of money paid.
 * Sixth column: balance of the customer on the date.
 * Seventh column: amount of returned twenty canisters.
 * Eighth column: balance of twenty canisters.
 * Ninth column: amount of returned twelve canisters.
 * Tenth column: balance of twelve canisters.
 * Eleventh column: total balance of canisters.
 * @author Guido Chia
 */
public interface ExcelWriter {
    /**
     * Writes a new buy with the info given in a BuyInfo.
     * Requires a BuyInfo object non-null and with non-nulls returns on its methods.
     * If the customer is new, it creates the correspondent file.
     * @param info BuyInfo object that contains the info of the buy.
     */
    void WriteBuy(BuyInfo info, PriceInfo prices);
}