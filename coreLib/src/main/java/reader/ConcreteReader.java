package reader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import customer.ConcreteCustomer;
import customer.Customer;
import exceptions.WorkbookException;
import infos.ConcreteOutputInfo;
import infos.OutputInfo;


public class ConcreteReader implements ExcelReader {

    private static ConcreteReader INSTANCE;

    public static ConcreteReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConcreteReader();
        }
        return INSTANCE;
    }

    private ConcreteReader() {
    }

    @Override
    public OutputInfo readInfo(File customerFile) throws WorkbookException {
        OutputInfo res;
        Workbook customerWorkbook;

        customerWorkbook = getWorkbook(customerFile);

        Sheet sheet = customerWorkbook.getSheetAt(0);
        Row lastRow = getLastRow(sheet);

        res = getInfo(lastRow);

        try {
            customerWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    private Workbook getWorkbook(File customerFile) throws WorkbookException {
        Workbook customerWorkbook;
        try {
            customerWorkbook = WorkbookFactory.create(customerFile);
        } catch (IOException e) {
            throw new WorkbookException();
        } catch (InvalidFormatException e) {
            throw new WorkbookException();
        }
        return customerWorkbook;
    }

    @Override
    public Collection<Customer> readCostumers(int[] months, int year, File directory) {
        List<Customer> res = new LinkedList<>();

        File folder = new File(directory, "Ypora Clientes");
        File[] subFolders = folder.listFiles();

        for (File f : subFolders) {
            readCostumersFromSubFolder(f, res, months, year);
        }

        return res;
    }

    /**
     * Reads the customers from the folder to the customer list
     *
     * @param folder Folder that contains the excel files
     * @param list   Customer list
     * @param months Month to look in
     * @param year   Year to look in
     */
    private void readCostumersFromSubFolder(File folder, List<Customer> list, int[] months, int year) {
        File[] files = folder.listFiles();

        for (File f : files) {
            if (f.getName().endsWith(".xls")) {
                Customer c = convertToCustomer(f, months, year);
                if (!c.isEmpty()) {
                    list.add(c);
                }
            }
        }
    }

    /**
     * Converts the given file to a Customer object
     *
     * @param f      File of the customer
     * @param months Month to look in
     * @param year   Year too look in
     * @return Customer object containing the info of the given month of the customer
     */
    private Customer convertToCustomer(File f, int[] months, int year) {


        Workbook customerWorkbook = null;
        try {
            customerWorkbook = getWorkbook(f);
        } catch (WorkbookException e) {
            // This should never happen since all files exists and are readable.
            e.printStackTrace();
            return null;
        }

        Sheet customerSheet = customerWorkbook.getSheetAt(0);
        String name = customerSheet.getRow(0).getCell(0).getStringCellValue();
        Customer res = new ConcreteCustomer(name);

        int currentRow = 3;

        boolean finish = false;

        if (isEmpty(customerSheet.getRow(currentRow).getCell(0)))
            finish = true;

        while (!finish) {
            Row row = customerSheet.getRow(currentRow);
            Cell dateCell = row.getCell(row.getFirstCellNum());

            if (belongsDate(dateCell, months, year)) {
                addRowToCustomer(row, res);
            }

            if (customerSheet.getRow(currentRow + 1) != null) {
                if (isEmpty(customerSheet.getRow(currentRow + 1).getCell(0))) {
                    finish = true;
                } else {
                    currentRow++;
                }
            }
            else finish = true;
        }

        return res;
    }

    /**
     * Determines if the date stored in the dateCell belongs to the given year and month
     *
     * @param dateCell The cell containing the date
     * @param months   The months to check
     * @param year     The year to check
     * @return true if the dates has the same month and year, false otherwise
     */
    private boolean belongsDate(Cell dateCell, int[] months, int year) {


        String dateString = dateCell.getStringCellValue();
        System.out.println(dateString);
        String[] strings = dateString.split("/");


        boolean equalMonth = false;
        int i = 0;
        while (!equalMonth && i < months.length) {
            equalMonth = Integer.parseInt(strings[1]) == months[i];
            i++;
        }
        boolean equalYear = Integer.parseInt(strings[2]) == year;

        return equalMonth && equalYear;
    }


    /**
     * Updates the customer with the info from the row
     *
     * @param row Row containing the info
     * @param c   The customer
     */
    private void addRowToCustomer(Row row, Customer c) {
        String dateString = row.getCell(0).getStringCellValue();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int twelveAmount = (int) row.getCell(1).getNumericCellValue();


        int twentyAmount = (int) row.getCell(2).getNumericCellValue();

        double paid = row.getCell(4).getNumericCellValue();
        System.out.println(paid);
        c.addUpdate(date, twelveAmount, twentyAmount, paid);
    }

    /**
     * Looks for the row with the last sell.
     *
     * @param sheet The sheet to look in.
     * @return The row with the last sell.
     */
    private Row getLastRow(Sheet sheet) {
        Row res = null;

        int startRow = 3;

        boolean found = false;

        res = sheet.getRow(startRow);
        while (!found) {
            Row row = sheet.getRow(startRow + 1);

            if ((row == null) || isEmpty(row.getCell(row.getFirstCellNum()))) {
                found = true;
            } else {
                res = row;
                startRow++;
            }
        }

        return res;
    }

    /**
     * Checks if the given cell is empty.
     * A cell is considered empty if is null, or is of the CellType.BLANK type, or if is a String cell
     * with an empty string.
     *
     * @param c The cell to check if is empty
     * @return true if the cell is empty, false otherwise.
     */
    private boolean isEmpty(Cell c) {
        return (c == null || (c.getCellTypeEnum() == CellType.BLANK) || (c.getCellTypeEnum() == CellType.STRING && c.getStringCellValue().trim().isEmpty()));
    }

    /**
     * Initializes and returns an OutputInfo object with the info from the given row
     *
     * @param row The row containing the info.
     * @return OutputInfo with the info from the row.
     */
    private OutputInfo getInfo(Row row) {

        String dateString = row.getCell(0).getStringCellValue();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        FormulaEvaluator evaluator = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();

        CellValue value = evaluator.evaluate(row.getCell(5));
        double balance = value.getNumberValue();

        value = evaluator.evaluate(row.getCell(7));
        int twentyBalance = (int) value.getNumberValue();

        value = evaluator.evaluate(row.getCell(9));
        int twelveBalance = (int) value.getNumberValue();

        return new ConcreteOutputInfo(date, balance, twentyBalance, twelveBalance);
    }
}
