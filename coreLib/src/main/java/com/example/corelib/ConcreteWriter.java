package com.example.corelib;

import org.apache.commons.collections4.iterators.ArrayIterator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.WorkbookFactory.*;

/**
 * Implementation of the ExcelWriter interface
 * @author Guido Chia
 */
public class ConcreteWriter implements ExcelWriter {

    private static final String baseDir="/Ypora Clientes/";

    /**
     * Creates a new instance of ConcreteWriter
     */
    public ConcreteWriter(){   }

    @Override
    public void WriteBuy(BuyInfo info, PriceInfo prices) {
        String name = info.getName();
        File file = findFile(name);

        Workbook customerWorkbook= createWorkbook(file, name);

        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        Row lastRow = moveToEnd(customerSheet);

        initializeRow(lastRow, info, prices);

        try {
            customerWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the workbook for the given file. If the file doesn't exist,
     * it creates the excel file of type .xls .
     * @param file Excel file.
     * @return The workbook of the given file
     */
    private Workbook createWorkbook(File file, String name){
        Workbook res=null;
        if (file.length() != 0){
            try {
                res = WorkbookFactory.create(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        else{
            res = new HSSFWorkbook();
            initWorkbook(res,  file, name);
        }
        return res;
    }

    public void initWorkbook(Workbook workbook, File file, String name){
        Sheet sheet = workbook.createSheet();

        /*
        Create the first row, with the name of the customer
         */
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(name);

        /*
        Create the third row, with the titles.
         */
        row = sheet.createRow(2);
        initTitles(row);


    }

    /**
     * Finds the file of the customer
     * If it doesn't exist, it creates one.
     * @param name the name of the customer
     * @return the File instance of the file opened.
     */
    private File findFile(final String name){
        File directory = new File(baseDir+name.charAt(0));
        directory.mkdirs();

        File res = new File(baseDir+name.charAt(0)+"/"+name+".xls");
        try {
            res.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Looks for the first empty row in the given sheet.
     * @param sheet The given sheet.
     * @return The first empty row.
     */
    private Row moveToEnd(Sheet sheet){
        Row res = null;

        Iterator<Row> it = sheet.iterator();

        while (it.hasNext()){
            Row row = it.next();
            if (isEmpty(row.getCell(row.getFirstCellNum()))){
                res=row;
            }
        }

        if (res==null){
            res= sheet.createRow(sheet.getLastRowNum());
        }

        return res;
    }

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

        /*
        Initialize the first cell, the date.
         */
        Cell currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getDate());
        cellIndex++;

        /*
        Initialize the second cell, the amount of twenty canisters bought.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getTwentyCanister());
        cellIndex++;

        /*
        Initialize the third cell, the amount of twelve canisters bought.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getTwelveCanister());
        cellIndex++;

        /*
        Calculate the forth cell, the total price of the buy.
         */
        String formula = "B"+lastRow.getRowNum()+"*"+prices.getTwentyPrice()
                +"+C"+lastRow.getRowNum()+"*"+prices.getTwelvePrice();
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellType(CellType.FORMULA);
        currentCell.setCellFormula(formula);
        cellIndex++;

        /*
        Initialize the fifth cell, the amount of money paid
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getPaid());
        cellIndex++;

        /*
        Calculate the sixth cell, the customer's balance.
         */
        cellIndex++;

        /*
        Initialize the seventh cell, the amount of returned twenty canisters.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getReturnedCanister());
        cellIndex++;

        /*
        Calculate the eighth cell, the balance of twenty canisters.
         */
        cellIndex++;

        /*
        Initialize the  ninth cell, the amount of returned twelve canisters.
         */
        currentCell = lastRow.createCell(cellIndex);
        currentCell.setCellValue(info.getTwentyCanister());
        cellIndex++;

        /*
        Calculate the tenth cell, the balance of twelve canisters.
         */
        cellIndex++;

        /*
        Calculate the eleventh cell, the total balance of canisters.
         */

    }

    /**
     * Initializes the row with the titles
     * @param row The row to initialize
     */
    private void initTitles(Row row){

        String [] titles = {"Fecha","20","12","Total","Pagó","Saldo",
        "Envases devueltos 20","Envases 20", "Envases devueltos 12",
        "Envases 12", "Envases totales"};

        for (int index=0; index<titles.length;index++){
            Cell cell = row.createCell(index);
            cell.setCellValue(titles[index]);
        }

    }
}
