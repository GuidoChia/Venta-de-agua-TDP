package writer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import customer.Customer;
import infos.BuyInfo;
import infos.PriceInfo;


/**
 * Implementation of the ExcelWriter interface
 *
 * @author Guido Chia
 */
public class ConcreteWriter implements ExcelWriter {

    private CellStyle defaultStyle;
    private RowInitializer rowInitializer;
    private static ExcelWriter INSTANCE;

    /**
     * Gets the singleton instance of ExcelWriter
     *
     * @return instance of ExcelWriter
     */
    public static ExcelWriter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConcreteWriter();
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of ConcreteWriter
     */
    private ConcreteWriter() {
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        rowInitializer = new ConcreteRowInitializer();
    }

    @Override
    public void WriteBuy(BuyInfo info, PriceInfo prices, File file) {
        String name = info.getName();
        Workbook customerWorkbook = createWorkbook(name, file);

        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        Row lastRow = moveToEnd(customerSheet);

        initBuyRow(lastRow, info, prices);

        /*
        Autosize columns to fit content. Since autoSizeColumn method doesn't work on android
        (missing java.awt files) they will be manually defined from a test.
         */
        int[] sizes = {2828, 835, 835, 1404, 1689, 1575, 5275, 2941,
                5275, 2941, 5275, 2941, 3880, 2062};

        int columnsAmount = 12;
        for (int i = 0; i < columnsAmount; i++) {
            customerSheet.setColumnWidth(i, sizes[i]);
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            customerWorkbook.write(fileOut);
            fileOut.close();
            customerWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void WriteRoute(Collection<Customer> customers, File file) {
        Workbook routeWorkbook = new HSSFWorkbook();
        defaultStyle = getDefaultStyle(routeWorkbook);
        Sheet routeSheet = routeWorkbook.createSheet();

        rowInitializer.initRouteTitles(routeSheet, defaultStyle);

        writeRouteCustomers(routeSheet, customers);

        /*
        Autosize columns to fit content. Since autoSizeColumn method doesn't work on android
        (missing java.awt files) they will be manually defined from a test.
         */
        int[] sizes = {7851, 1400, 1400, 2532, 3532, 6000};
        int columnsAmount = 6;
        for (int i = 0; i < columnsAmount; i++) {
            routeSheet.setColumnWidth(i, sizes[i]);
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            routeWorkbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRouteCustomers(Sheet routeSheet, Collection<Customer> customers) {
        int i = 1;

        Iterator<Customer> customerIterator = customers.iterator();

        Row currentRow;
        while (customerIterator.hasNext()) {
            currentRow = routeSheet.createRow(i);
            currentRow.setHeight((short) 310);
            rowInitializer.initRouteRow(currentRow, customerIterator.next(), defaultStyle);
            i++;
        }


    }


    /**
     * Creates a new workbook for the name given.
     *
     * @param name Name of the customer
     * @param file File to read the workbook if it exists.
     * @return The workbook of the given file
     */
    private Workbook createWorkbook(String name, File file) {
        Workbook res = null;
        if (file.length() != 0) {
            try {
                FileInputStream in = new FileInputStream(file);
                res = WorkbookFactory.create(in);
                in.close();
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        } else {
            res = new HSSFWorkbook();
            initWorkbook(res, name);
        }
        return res;
    }

    /**
     * Initializes the workbook with the given name and the titles.
     *
     * @param workbook Workbook to initialize.
     * @param name     Customer's name.
     */
    private void initWorkbook(Workbook workbook, String name) {
        Sheet sheet = workbook.createSheet();

        /*
        Create the first row, with the name of the customer
         */
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(range);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(name);
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(style);

        /*
        Create the third row, with the titles.
         */
        row = sheet.createRow(2);
        initBuyTitles(row);


    }

    /**
     * Looks for the first empty row in the given sheet.
     *
     * @param sheet The given sheet.
     * @return The first empty row.
     */
    private Row moveToEnd(Sheet sheet) {
        /*
        This method is needed in case the sheet has been created outside this program.
        If it was created with this program, the first empty row is the one with the
        index sheet.getLastRowIndex(). But as it's unknown if the sheet was or not created
        by this program, its needed to check in this way.
         */

        Row res = null;
        /*
        The start row is the number 3, after the row with the titles.
         */

        int startRow = 3;

        boolean found = false;

        while (!found) {
            Row row = sheet.getRow(startRow);
            if ((row == null) || isEmpty(row.getCell(0))) {
                res = row;
                found = true;
            } else {
                startRow++;
            }
        }


        if (res == null) {
            res = sheet.createRow(startRow);
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
     * Initializes the cells of the given row, with the given info.
     *
     * @param lastRow The first empty row
     * @param info    The BuyInfo object
     * @param prices  The PriceInfo object.
     */
    private void initBuyRow(Row lastRow, BuyInfo info, PriceInfo prices) {
        CellStyle style = getDefaultStyle(lastRow.getSheet().getWorkbook());
        style.setAlignment(HorizontalAlignment.RIGHT);
        rowInitializer.initBuyRow(lastRow, info, prices, style);
    }

    /**
     * Initializes the row with the titles
     *
     * @param row The row to initialize
     */
    private void initBuyTitles(Row row) {
        CellStyle style = getDefaultStyle(row.getSheet().getWorkbook());
        style.setAlignment(HorizontalAlignment.CENTER);
        rowInitializer.initBuyTitles(row, style);
    }


    /**
     * Creates an outlined style with vertical aligment center
     *
     * @param workbook Workbook where the the style will apply
     * @return The cellStyle with the defaut configuration
     */
    private CellStyle getDefaultStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        return style;
    }
}
