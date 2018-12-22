package com.example.corelib;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

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
    public void WriteBuy(BuyInfo info) {
        String name = info.getName();
        File file = findFile(name);

        WorkbookFactory factory = new WorkbookFactory();
        Workbook customerWorkbook = factory.create(file);
        Sheet customerSheet = customerWorkbook.getSheetAt(0);

        Row lastRow = moveToEnd(customerSheet);

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

    private Row moveToEnd(Sheet sheet){

    }
}
