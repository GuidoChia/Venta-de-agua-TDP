package writer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import infos.BuyInfo;
import infos.PriceInfo;
import reader.ConcreteReader;


/**
 * Implementation of the ExcelWriter interface
 * @author Guido Chia
 */
public class ConcreteWriter implements ExcelWriter {

    private static ConcreteWriter INSTANCE;
    private static final String baseDir="/sdcard/Ypora Clientes/";
    private final int columnsAmount = 12;
    private Row row;

    public static ConcreteWriter getInstance(){
        if (INSTANCE==null){
            INSTANCE=new ConcreteWriter();
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of ConcreteWriter
     */
    private ConcreteWriter(){}

    @Override
    public void WriteBuy(BuyInfo info, PriceInfo prices, File file) {
        String name = info.getName();
        Workbook customerWorkbook= createWorkbook(name, file);

        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        Row lastRow = moveToEnd(customerSheet);

        initializeRow(lastRow, info, prices);

        /*
        Autosize columns to fit content. Since autoSizeColumn doesn't work on android (missing
        java.awt files) they will be manually defined from a test.
         */
        int [] sizes ={2828, 835, 835, 1404, 1689, 1575, 5275, 2941,
        5275, 2941,  5275, 2941, 3880, 2062};

        for (int  i=0; i<columnsAmount; i++){
            customerSheet.setColumnWidth(i,sizes[i]);
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

    /**
     * Creates a new workbook for the name given.
     * @param name Name of the customer
     * @param file File to read the workbook if it exists.
     * @return The workbook of the given file
     */
    private Workbook createWorkbook(String name, File file){
        Workbook res=null;
        if (file.length() != 0){
            try {
                res = WorkbookFactory.create(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        else{
            res = new HSSFWorkbook();
            initWorkbook(res, name);
        }
        return res;
    }

    /**
     * Initializes the workbook with the given name and the titles.
     * @param workbook Workbook to initialize.
     * @param name Customer's name.
     */
    private void initWorkbook(Workbook workbook, String name){
        Sheet sheet = workbook.createSheet();

        /*
        Create the first row, with the name of the customer
         */
        CellRangeAddress range = new CellRangeAddress(0,0,0,10);
        sheet.addMergedRegion(range);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(name);
        CellStyle style= workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(style);

        /*
        Create the third row, with the titles.
         */
        row = sheet.createRow(2);
        initTitles(row);


    }

    /**
     * Looks for the first empty row in the given sheet.
     * @param sheet The given sheet.
     * @return The first empty row.
     */
    private Row moveToEnd(Sheet sheet){
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

        while (!found){
            Row row = sheet.getRow(startRow);
            if ((row==null)||isEmpty(row.getCell(row.getFirstCellNum()))){
                res=row;
                found=true;
            }
            else{
                startRow++;
            }
        }


        if (res==null){
            res= sheet.createRow(startRow);
        }

        return res;
    }

    /**
     * Checks if the given cell is empty.
     * A cell is considered empty if is null, or is of the CellType.BLANK type, or if is a String cell
     * with an empty string.
     * @param c The cell to check if is empty
     * @return true if the cell is empty, false otherwise.
     */
    private boolean isEmpty(Cell c){
        return ( c==null ||  (c.getCellTypeEnum() == CellType.BLANK ) ||  (c.getCellTypeEnum() == CellType.STRING && c.getStringCellValue().trim().isEmpty()));
    }

    /**
     * Initializes the cells of the given row, with the given info.
     * @param lastRow The first empty row
     * @param info The BuyInfo object
     * @param prices The PriceInfo object.
     */
    private void initializeRow(Row lastRow, BuyInfo info, PriceInfo prices){
        int cellIndex=0;
        String formula,
                postFix;
        formula = "";
        postFix = "";

        /*
        This is the row num of the lastRow, but plus one to use it in formulas.
         */
        int lastRowNum=lastRow.getRowNum()+1;

        /*
        Initialize the first cell, the date.
         */
        Cell currentCell = lastRow.createCell(cellIndex);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(info.getDate());
        currentCell.setCellValue(format);
        cellIndex++;

        /*
        Initialize the second cell, the amount of twenty canisters bought.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(info.getTwentyCanister());
        cellIndex++;

        /*
        Initialize the third cell, the amount of twelve canisters bought.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(info.getTwelveCanister());
        cellIndex++;

        /*
        Calculate the forth cell, the total price of the buy.
         */

        formula = "B"+lastRowNum+"*"+prices.getTwentyPrice()
                +"+C"+lastRowNum+"*"+prices.getTwelvePrice();
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);
        cellIndex++;

        /*
        Initialize the fifth cell, the amount of money paid
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(info.getPaid());
        cellIndex++;

        /*
        Calculate the sixth cell, the customer's balance.
         */
        if (isFirstRow(lastRowNum)){
            postFix="";
        }
        else{
            postFix="+F"+(lastRowNum-1);
        }

        formula = "D"+lastRowNum+"-E"+lastRowNum+postFix;
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);
        cellIndex++;

        /*
        Initialize the seventh cell, the amount of returned twenty canisters.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(info.getTwentyReturnedCanister());
        cellIndex++;

        /*
        Calculate the eighth cell, the balance of twenty canisters.
         */
        if (isFirstRow(lastRowNum)){
            postFix="";
        }
        else{
            postFix="+H"+(lastRowNum-1);
        }

        formula = "B"+lastRowNum+"-G"+lastRowNum+postFix;
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);
        cellIndex++;

        /*
        Initialize the  ninth cell, the amount of returned twelve canisters.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(info.getTwelveReturnedCanister());
        cellIndex++;

        /*
        Calculate the tenth cell, the balance of twelve canisters.
         */
        if (isFirstRow(lastRowNum)){
            postFix="";
        }
        else{
            postFix="+J"+(lastRowNum-1);
        }

        formula = "C"+lastRowNum+"-I"+lastRowNum+postFix;
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);
        cellIndex++;

        /*
        Calculate the eleventh cell, the total balance of canisters.
         */

        formula = "H"+lastRowNum+"+J"+lastRowNum;
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);

    }

    /**
     * Initializes the row with the titles
     * @param row The row to initialize
     */
    private void initTitles(Row row){
        this.row = row;

        row.setHeightInPoints(40);

        CellStyle style= row.getSheet().getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        String [] titles = {"Fecha","20","12","Total","Pagó","Saldo",
        "Envases devueltos 20","Envases 20", "Envases devueltos 12",
        "Envases 12", "Envases totales"};

        for (int index=0; index<titles.length;index++){
            Cell cell = row.createCell(index);
            cell.setCellValue(titles[index]);
            cell.setCellStyle(style);
        }

    }

    /**
     * Checks if the given rowNum corresponds to the first row (row 4).
     * @param rowNum Number of the row.
     * @return true if the row is the first after the titles, false otherwise.
     */
    private boolean isFirstRow(int rowNum){
        return (rowNum<=4);
    }
}
