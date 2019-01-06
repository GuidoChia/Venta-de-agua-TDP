package reader;

import java.io.FileNotFoundException;

import infos.OutputInfo;


/**
 * The interface that represents a reader of OutputInfo from an excel file.
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
 * @author Guido Chia
 */
public interface ExcelReader {
    /**
     * Reads the info of an excel from the given name, and stores it in an OutputInfo object.
     * @param name Name of the customer
     * @throws FileNotFoundException if the given name is not from a registered customer.
     * @return OutputInfo containing the info read from the excel.
     */
    OutputInfo readInfo(String name) throws FileNotFoundException;
}
