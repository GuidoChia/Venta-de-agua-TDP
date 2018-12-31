package com.example.corelib;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Iterator;

/**
 * Implementation of the ExcelWriter interface
 * @author Guido Chia
 */
public class ConcreteWriter implements ExcelWriter {

    private static final String baseDir="/Ypora Cliente/"

    /**
     * Creates a new instance of ConcreteWriter
     */
    public ConcreteWriter(){

    }

    @Override
    public void WriteBuy(BuyInfo info, PriceInfo prices) {
        String name = info.getName();
        File file = findFile(name);

        Workbook customerWorkbook = WorkbookFactory.create(file);
        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        Row lastRow = moveToEnd(customerSheet);

        int cellIndex = lastRow.getFirstCellNum();

        Cell currentCell = lastRow.getCell(++cellIndex);
        currentCell.setCellValue(info.getDate());

        currentCell = lastRow.getCell(++cellIndex);
        currentCell.setCellValue(info.getTwentyCanister());

        currentCell = lastRow.getCell(++cellIndex);
        currentCell.setCellValue(info.getTwelveCanister());

        currentCell = lastRow.getCell(++cellIndex);
        currentCell.setCellValue(info.getReturnedCanister());

        currentCell = lastRow.getCell(++cellIndex);
        currentCell.setCellValue(info.getTwentyCanister());

    }

    /**
     * Finds the file of the customer
     * If it doesn't exist, it creates one.
     * @param name the name of the customer
     * @return the File instance of the file opened.
     */
    private File findFile(final String name){
        File directory = new File(baseDir+name.charAt(0));
        File res = new File(baseDir+name.charAt(0)+"/"+name+".xls");
        res.createNewFile();
    }

    /**
     * Looks for the first empty row in the given sheet.
     * @param sheet The given sheet.
     * @return The first empty row.
     */
    private Row moveToEnd(Sheet sheet){
        Row res = null;

        Iterator<Row> it = sheet.iterator();

        while (it.hasNext() || res!=null){
            Row row = it.next();
            if (isEmpty(row.getCell(row.getFirstCellNum()))){
                res=row;
            }
        }

        return res;
    }

    private boolean isEmpty(Cell c){
        return ( (c.getRichStringCellValue().getString().isEmpty()) || c==null );
    }
}
