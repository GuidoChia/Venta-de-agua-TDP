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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.WorkbookException;
import infos.ConcreteOutputInfo;
import infos.OutputInfo;


public class ConcreteReader implements ExcelReader {

    private static ConcreteReader INSTANCE;

    public static ConcreteReader getInstance(){
        if (INSTANCE==null){
            INSTANCE = new ConcreteReader();
        }
        return INSTANCE;
    }

    private ConcreteReader(){}

    @Override
    public OutputInfo readInfo(File customerFile) throws WorkbookException {
        OutputInfo res;
        Workbook customerWorkbook = null;
        try {
            customerWorkbook = WorkbookFactory.create(customerFile);
        } catch (IOException e) {
            throw new WorkbookException();
        } catch (InvalidFormatException e) {
            throw new WorkbookException();
        }

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
