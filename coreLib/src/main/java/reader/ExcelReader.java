package reader;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import customer.Customer;
import exceptions.WorkbookException;
import infos.OutputInfo;


/**
 * The interface that represents a reader from an excel file.
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
public interface ExcelReader {
    /**
     * Reads the info of the given file, and stores it in an OutputInfo object.
     *
     * @param customerFile the file of the customer.
     * @return OutputInfo containing the info read from the excel.
     * @throws WorkbookException if the file doesn't exist, or it's not available.
     */
    OutputInfo readInfo(File customerFile) throws WorkbookException;

    /**
     * Reads all the costumers from the given directory, and creates Customer objects for them,
     * with the info of the given dates.
     *
     * @param years      Dates from the years that wants to be read. Day and month will be ignored
     * @param directory Directory in which the customer's folder will be in
     * @return Collection of Customer that bought anything in the given month and year
     */
    Collection<Customer> readCustomersYear(Date[] years, File directory);

    /**
     * Reads all the costumers from the given directory, and creates Customer objects for them,
     * with the info of the given dates.
     *
     * @param months    Dates from the months that wants to be read. The day here will be ignored
     * @param directory Directory in which the customer's folder will be
     * @return Collection of Customer that bought anything in the given month and year
     */
    Collection<Customer> readCustomersMonth(Date[] months, File directory);

    /**
     * Reads all the costumers from the given directory, and creates Customer objects for them,
     * with the info of the given dates.
     *
     * @param days      Dates that wants to be read.
     * @param directory Directory in which the customer's folder will be in
     * @return Collection of Customer that bought anything in the given month and year
     */
    Collection<Customer> readCustomersDays(Date[] days, File directory);
}
