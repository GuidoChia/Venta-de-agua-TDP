package reader;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;

import exceptions.WorkbookException;
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
     * Reads the info of the given file, and stores it in an OutputInfo object.
     * @param customerFile the file of the customer.
     * @return OutputInfo containing the info read from the excel.
     * @throws WorkbookException if the customer Workbook is damaged or null.
     */
    OutputInfo readInfo(File customerFile) throws WorkbookException;
}
