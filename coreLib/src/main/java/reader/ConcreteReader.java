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

import customer.ConcreteCustomer;
import customer.Customer;
import exceptions.WorkbookException;
import infos.ConcreteOutputInfo;
import infos.OutputInfo;
import utils.Pair;


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
        if (!customerFile.exists()) {
            throw new WorkbookException();
        } else {
            try {
                customerWorkbook = WorkbookFactory.create(customerFile);
            } catch (IOException e) {
                throw new WorkbookException();
            } catch (InvalidFormatException e) {
                throw new WorkbookException();
            }
        }
        return customerWorkbook;
    }

    @Override
    public Collection<Customer> readCostumers(Pair<Integer, Integer>[] monthsAndYears, File directory) {
        List<Customer> res = new LinkedList<>();

        File folder = new File(directory, "Ypora Clientes");
        File[] subFolders = folder.listFiles();

        for (File f : subFolders) {
            readCostumersFromSubFolder(f, res, monthsAndYears);
        }

        return res;
    }

    /**
     * Reads the customers from the folder to the customer list
     *
     * @param folder Folder that contains the excel files
     * @param list   Customer list
     * @param monthsAndYears array containing pairs of month and years to check
     */
    private void readCostumersFromSubFolder(File folder, List<Customer> list, Pair<Integer, Integer>[] monthsAndYears) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            for (File f : files) {
                if (f.getName().endsWith(".xls")) {
                    Customer c = convertToCustomer(f, monthsAndYears);
                    if (!c.isEmpty()) {
                        list.add(c);
                    }
                }
            }
        }
    }

    /**
     * Converts the given file to a Customer object
     *
     * @param f      File of the customer
     * @param monthsAndYears array containing pairs of month and years to check
     * @return Customer object containing the info of the given month of the customer
     */
    private Customer convertToCustomer(File f, Pair<Integer, Integer>[] monthsAndYears) {


        Workbook customerWorkbook = null;
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

        int currentRow = 3;

        boolean finish = false;

        if (isEmpty(customerSheet.getRow(currentRow).getCell(0)))
            finish = true;


        /*
        Checkeo por lineas si la fecha corresponde a los meses ingresados
        Termino cuando encuentro una linea vacía.
         */
        while (!finish) {
            Row row = customerSheet.getRow(currentRow);
            Cell dateCell = row.getCell(row.getFirstCellNum());

            if (belongsDate(dateCell, monthsAndYears)) {
                addRowToCustomer(row, res);
            }

            if (customerSheet.getRow(currentRow + 1) != null) {
                if (isEmpty(customerSheet.getRow(currentRow + 1).getCell(0))) {
                    finish = true;
                } else {
                    currentRow++;
                }
            } else finish = true;
        }

        try {
            customerWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Checks if the name is written correctly, if not it corrects it.
     * @param name The name to check
     * @return The name correctly written
     */
    private String getName(File f){
        String res = f.getName();
        res = res.substring(0,res.length()-4);

        return res;
    }

    /**
     * Determines if the date stored in the dateCell belongs to the given years and months
     *
     * @param dateCell The cell containing the date
     * @param monthsAndYears array containing pairs of month and years to check
     * @return true if the dates has the same month and year, false otherwise
     */
    private boolean belongsDate(Cell dateCell, Pair<Integer, Integer>[] monthsAndYears) {


        String dateString;
        if (dateCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            Date date = dateCell.getDateCellValue();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dateString = format.format(date);
        } else {
            dateString = dateCell.getStringCellValue();
        }

        String[] strings = dateString.split("/");


        boolean equalMonth;
        boolean equalYear;
        boolean endEqual = false;
        int i = 0;
        while (!endEqual && i < monthsAndYears.length) {
            equalMonth = Integer.parseInt(strings[1]) == monthsAndYears[i].first;
            equalYear = Integer.parseInt(strings[2]) == monthsAndYears[i].second;
            endEqual = equalMonth && equalYear;
            i++;
        }

        return endEqual;
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
        Date date = null;

        if (dateCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
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


        int twentyAmount = (int) row.getCell(1).getNumericCellValue();


        int twelveAmount = (int) row.getCell(2).getNumericCellValue();


        double paid = row.getCell(4).getNumericCellValue();

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
        Cell cell = row.getCell(0);
        Date date = null;
        if (cell.getCellTypeEnum().equals(CellType.NUMERIC))
            date = cell.getDateCellValue();
        else {
            String dateString = cell.getStringCellValue();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
