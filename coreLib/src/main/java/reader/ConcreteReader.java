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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import customer.ConcreteCustomer;
import customer.Customer;
import exceptions.WorkbookException;
import infos.ConcreteOutputInfo;
import infos.OutputInfo;
import strategies.DateStrategy;
import strategies.DayStrategy;
import strategies.YearMonthStrategy;
import strategies.YearStrategy;


public class ConcreteReader implements ExcelReader {

    private static ExcelReader INSTANCE;

    /**
     * Gets the singleton instance of the Writer
     *
     * @return the ExcelReader instance
     */
    public static ExcelReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConcreteReader();
        }
        return INSTANCE;
    }

    /**
     * Creates an instance of ConcreteReader
     */
    private ConcreteReader() {
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
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

    @Override
    public Collection<Customer> readCustomersYear(Date[] years, File directory) {
        DateStrategy strategy = new YearStrategy(years);

        return readCostumers(strategy, directory);
    }

    @Override
    public Collection<Customer> readCustomersMonth(Date[] months, File directory) {
        DateStrategy strategy = new YearMonthStrategy(months);

        return readCostumers(strategy, directory);
    }

    @Override
    public Collection<Customer> readCustomersDays(Date[] days, File directory) {
        DateStrategy strategy = new DayStrategy(days);

        return readCostumers(strategy, directory);
    }

    private Workbook getWorkbook(File customerFile) throws WorkbookException {
        Workbook customerWorkbook;
        if (!customerFile.exists() || (customerFile.length() == 0)) {
            throw new WorkbookException();
        } else {
            try {
                InputStream in = new FileInputStream(customerFile);
                customerWorkbook = WorkbookFactory.create(in);
                in.close();
            } catch (IOException | InvalidFormatException e) {
                throw new WorkbookException();
            }
        }
        return customerWorkbook;
    }


    /**
     * Reads the customers following the given strategy
     *
     * @param strat     The strategy
     * @param directory Directory in which the customer's folder will be in
     * @return Customer collection containing the created Customers following de strategy given
     */
    private Collection<Customer> readCostumers(DateStrategy strat, File directory) {
        List<Customer> res = new LinkedList<>();

        File folder = new File(directory, "Ypora Clientes");
        folder.mkdirs();

        File[] subFolders = folder.listFiles();

        if (subFolders == null) {
            return res;
        }

        if (subFolders.length != 0) {
            Arrays.sort(subFolders);
        }

        for (File f : Objects.requireNonNull(subFolders)) {
            readCostumersFromSubFolder(f, res, strat);
        }

        return res;
    }

    /**
     * Reads the customers from the folder to the customer list
     *
     * @param folder Folder that contains the excel files
     * @param list   Customer list
     * @param strat  Strategy to follow
     */
    private void readCostumersFromSubFolder(File folder, List<Customer> list, DateStrategy strat) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files.length != 0) {
                Arrays.sort(files);
            }

            for (File f : Objects.requireNonNull(files)) {
                if (f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
                    if (f.length() != 0) {
                        Customer c = convertToCustomer(f, strat);
                        if (!Objects.requireNonNull(c).isEmpty()) {
                            list.add(c);
                        }
                    }
                }
            }
        }
    }

    /**
     * Converts the given file to a Customer object
     *
     * @param f     File of the customer
     * @param strat Strategy to follow
     * @return Customer object containing the info of the given month of the customer
     */
    private Customer convertToCustomer(File f, DateStrategy strat) {


        Workbook customerWorkbook;
        try {
            customerWorkbook = getWorkbook(f);
        } catch (WorkbookException e) {
            // This should never happen since all files exists and are readable.
            e.printStackTrace();
            return null;
        }

        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        String finalName = getName(f);
        Customer res = new ConcreteCustomer(finalName);

        int currentRow = customerSheet.getLastRowNum();

        boolean finish = false;

        /*
        Check line by line if the date corresponds with the given strategy.
        Stop when an empty line is found.
         */
        while (!finish && currentRow >= 3) {
            System.out.println(currentRow);
            Row row = customerSheet.getRow(currentRow);
            if (row == null || isEmpty(row.getCell(0))) {
                currentRow--;
            } else {
                Cell dateCell = row.getCell(0);
                if (belongsDate(dateCell, strat)) {
                    addRowToCustomer(row, res);
                    currentRow--;
                } else if (isSmaller(dateCell, strat)) {
                    finish = true;
                } else {
                    currentRow--;
                }
            }
        }

        if (!res.isEmpty()) {
            Row row = customerSheet.getRow(currentRow);
            FormulaEvaluator evaluator = customerWorkbook.getCreationHelper().createFormulaEvaluator();
            CellValue value = evaluator.evaluate(row.getCell(5));
            double balance = 0;
            if (value != null) {
                balance = value.getNumberValue();
                res.setBalance(balance);
            }
        }


        try {
            customerWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Gets the name from a file without its extensions
     *
     * @return The name
     */
    private String getName(File f) {
        String res = f.getName();
        res = res.substring(0, res.length() - 4);

        return res;
    }

    /**
     * Determines if the date stored in the dateCell is smaller than the given criteria
     *
     * @param dateCell The cell containing the date
     * @param strat Strategy to follow
     * @return true if the date is smaller than the given criteria, false otherwise
     */
    private boolean isSmaller(Cell dateCell, DateStrategy strat){
        boolean res = false;

        Date date = getDate(dateCell);

        if (date!=null){
            res = strat.isSmaller(date);
        }

        return res;
    }
    /**
     * Determines if the date stored in the dateCell belongs to the given years and months
     *
     * @param dateCell The cell containing the date
     * @param strat    Strategy to follow
     * @return true if the dates has the same month and year, false otherwise
     */
    private boolean belongsDate(Cell dateCell, DateStrategy strat) {
        boolean res = false;

        Date date = getDate(dateCell);

        if (date != null) {
            res = strat.belongsDate(date);
        }

        return res;
    }


    /**
     * Updates the customer with the info from the row
     *
     * @param row Row containing the info
     * @param c   The customer
     */
    private void addRowToCustomer(Row row, Customer c) {
        String dateString;
        Cell dateCell = row.getCell(0);

        Date date = getDate(dateCell);


        int twentyAmount = (int) row.getCell(1).getNumericCellValue();

        int twelveAmount = (int) row.getCell(2).getNumericCellValue();

        double paid = row.getCell(4).getNumericCellValue();

        /*
        Only adds if the date was parseable
         */
        if (date != null) {
            c.addUpdate(date, twelveAmount, twentyAmount, paid);
        }
    }

    private Date getDate(Cell dateCell) {
        String dateString;
        Date date = null;
        if (dateCell.getCellTypeEnum() == CellType.NUMERIC) {
            date = dateCell.getDateCellValue();
        } else {
            dateString = dateCell.getStringCellValue();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * Looks for the row with the last sell.
     *
     * @param sheet The sheet to look in.
     * @return The row with the last sell.
     */
    private Row getLastRow(Sheet sheet) {
        Row res;

        int startRow = 3;

        boolean found = false;

        res = sheet.getRow(startRow);
        while (!found) {
            Row row = sheet.getRow(startRow + 1);

            if ((row == null) || isEmpty(row.getCell(0))) {
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
        return ((c == null) || (c.getCellTypeEnum() == CellType.BLANK) ||
                ((c.getCellTypeEnum() == CellType.STRING) && c.getStringCellValue().trim().isEmpty()));
    }

    /**
     * Initializes and returns an OutputInfo object with the info from the given row
     *
     * @param row The row containing the info.
     * @return OutputInfo with the info from the row.
     */
    private OutputInfo getInfo(Row row) {
        Cell cell = row.getCell(0);
        Date date = getDate(cell);

        FormulaEvaluator evaluator = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();

        CellValue value = evaluator.evaluate(row.getCell(5));
        double balance = 0;
        if (value != null) {
            balance = value.getNumberValue();
        }

        value = evaluator.evaluate(row.getCell(7));
        int twentyBalance = 0;
        if (value != null) {
            twentyBalance = (int) value.getNumberValue();
        }

        value = evaluator.evaluate(row.getCell(9));
        int twelveBalance = 0;
        if (value != null) {
            twelveBalance = (int) value.getNumberValue();
        }

        int twentyBought = (int) row.getCell(1).getNumericCellValue();

        int twelveBought = (int) row.getCell(2).getNumericCellValue();

        return new ConcreteOutputInfo(date, balance, twentyBalance, twelveBalance, twentyBought, twelveBought);
    }
}
