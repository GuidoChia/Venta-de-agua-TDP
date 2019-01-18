package writer;


import java.io.File;
import java.util.Collection;

import customer.Customer;
import infos.BuyInfo;
import infos.PriceInfo;

/**
 * The interface that represents a writer of BuyInfo in an excel file.
 * The file is assumed to have the next format:
 * First column: Date of buy.
 * Second column: amount of twenty canisters bought.
 * Third column: amount of twelve canisters bought.
 * Forth column: total price of the buy.
 * Fifth column: total amount of money paid.
 * Sixth column: customer's balance on the date.
 * Seventh column: amount of returned twenty canisters.
 * Eighth column: balance of twenty canisters.
 * Ninth column: amount of returned twelve canisters.
 * Tenth column: balance of twelve canisters.
 * Eleventh column: total balance of canisters.
 *
 * @author Guido Chia
 */
public interface ExcelWriter {
    /**
     * Writes a new buy with the info given in a BuyInfo, on the file given.
     * If the file doesn't exist, it creates one.
     * Requires a BuyInfo object non-null and with non-nulls returns on its methods.
     *
     * @param info   BuyInfo object that contains the info of the buy.
     * @param prices PriceInfo object that contais the prices.
     * @param file   The file to read and write.
     */
    void WriteBuy(BuyInfo info, PriceInfo prices, File file);

    /**
     * Writes a route in an excel file with the given customers.
     * The file and it's name must be provided by the client.
     * @param customers The customers to write.
     */
    void WriteRoute(Collection<Customer> customers, File file);
}