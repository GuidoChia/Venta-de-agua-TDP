package writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.SimpleDateFormat;

import customer.Customer;
import infos.BuyInfo;
import infos.PriceInfo;

public class ConcreteRowInitializer implements RowInitializer {
    @Override
    public void initRouteTitles(Sheet routeSheet, CellStyle defaultStyle) {
        Row firstRow = routeSheet.createRow(0);
        String[] titles = {"Nombre", "20", "12", "Pago",
                "Devueltos", "Observaciones"};
        Cell currentCell;

        for (int i = 0; i < titles.length; i++) {
            currentCell = firstRow.createCell(i);
            currentCell.setCellType(CellType.STRING);
            currentCell.setCellValue(titles[i]);
            currentCell.setCellStyle(defaultStyle);
        }
    }

    @Override
    public void initRouteRow(Row row, Customer customer, CellStyle defaultStyle) {
        int cellIndex = 0;
        Cell currentCell;
        currentCell = row.createCell(cellIndex);
        currentCell.setCellType(CellType.STRING);
        currentCell.setCellValue(customer.getName());
        currentCell.setCellStyle(defaultStyle);

        for (cellIndex = 1; cellIndex < 5; cellIndex++) {
            currentCell = row.createCell(cellIndex);
            currentCell.setCellStyle(defaultStyle);
        }

        cellIndex = 5;
        currentCell = row.createCell(cellIndex);
        currentCell.setCellStyle(defaultStyle);
        if (customer.getBalance() != 0) {
            currentCell.setCellType(CellType.NUMERIC);
            currentCell.setCellValue(customer.getBalance());
        }
    }

    @Override
    public void initBuyRow(Row lastRow, BuyInfo info, PriceInfo prices, CellStyle style) {
        int cellIndex = 0;
        String formula,
                postFix;

        Cell currentCell;

        /*
        This is the row num of the lastRow, but plus one to use it in formulas.
         */
        int lastRowNum = lastRow.getRowNum() + 1;

        /*
        Initialize the first cell, the date.
         */
        initFirstCellBuy(lastRow, info, style, cellIndex);
        cellIndex++;

        /*
        Initialize the second cell, the amount of twenty canisters bought.
         */
        initIntCellBuy(lastRow, style, cellIndex, info.getTwentyCanister());
        cellIndex++;

        /*
        Initialize the third cell, the amount of twelve canisters bought.
         */
        initIntCellBuy(lastRow, style, cellIndex, info.getTwelveCanister());
        cellIndex++;

        /*
        Calculate the forth cell, the total price of the buy.
         */

        initForthCellBuy(lastRow, prices, style, cellIndex, lastRowNum);
        cellIndex++;

        /*
        Initialize the fifth cell, the amount of money paid
         */
        initIntCellBuy(lastRow, style, cellIndex, (int) info.getPaid());
        cellIndex++;

        /*
        Calculate the sixth cell, the customer's balance.
         */
        initSixthCellBuy(lastRow, style, cellIndex, lastRowNum);
        cellIndex++;

        /*
        Initialize the seventh cell, the amount of returned twenty canisters.
         */
        initIntCellBuy(lastRow, style, cellIndex, info.getTwentyReturnedCanister());
        cellIndex++;

        /*
        Calculate the eighth cell, the balance of twenty canisters.
         */
        initFormulaCellBuy(lastRow, style, cellIndex, lastRowNum, "+H", "B", "-G");
        cellIndex++;

        /*
        Initialize the  ninth cell, the amount of returned twelve canisters.
         */
        initIntCellBuy(lastRow, style, cellIndex, info.getTwelveReturnedCanister());
        cellIndex++;

        /*
        Calculate the tenth cell, the balance of twelve canisters.
         */
        initFormulaCellBuy(lastRow, style, cellIndex, lastRowNum, "+J", "C", "-I");
        cellIndex++;

        /*
        Calculate the eleventh cell, the total balance of canisters.
         */
        initEleventhCellBuy(lastRow, style, cellIndex, lastRowNum);
    }

    private void initEleventhCellBuy(Row lastRow, CellStyle style, int cellIndex, int lastRowNum) {
        String formula;
        Cell currentCell;
        formula = "H" + lastRowNum + "+J" + lastRowNum;
        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        if (currentCell.getCellTypeEnum() != CellType.FORMULA) {
            currentCell.setCellFormula(null);
            currentCell.setCellType(CellType.FORMULA);
        }
        currentCell.setCellFormula(formula);
        currentCell.setCellStyle(style);
    }

    private void initFormulaCellBuy(Row lastRow, CellStyle style, int cellIndex, int lastRowNum, String s, String b, String s2) {
        String postFix;
        String formula;
        Cell currentCell;
        postFix = isFirstRow(lastRowNum) ? "" : s + (lastRowNum - 1);

        formula = b + lastRowNum + s2 + lastRowNum + postFix;
        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        if (currentCell.getCellTypeEnum() != CellType.FORMULA) {
            currentCell.setCellFormula(null);
            currentCell.setCellType(CellType.FORMULA);
        }
        currentCell.setCellFormula(formula);
        currentCell.setCellStyle(style);
    }

    private void initSixthCellBuy(Row lastRow, CellStyle style, int cellIndex, int lastRowNum) {
        String postFix;
        String formula;
        Cell currentCell;
        postFix = isFirstRow(lastRowNum) ? "" : "+F" + (lastRowNum - 1);


        formula = "D" + lastRowNum + "-E" + lastRowNum + postFix;
        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        if (currentCell.getCellTypeEnum() != CellType.FORMULA) {
            currentCell.setCellFormula(null);
            currentCell.setCellType(CellType.FORMULA);
        }
        currentCell.setCellFormula(formula);
        currentCell.setCellStyle(style);
        SheetConditionalFormatting formatting = lastRow.getSheet().getSheetConditionalFormatting();
        ConditionalFormattingRule rule = formatting.createConditionalFormattingRule(ComparisonOperator.GT, "0");
        PatternFormatting pattern = rule.createPatternFormatting();
        pattern.setFillBackgroundColor(IndexedColors.CORAL.index);
        CellRangeAddress addresses[] = {new CellRangeAddress(currentCell.getRowIndex(),
                currentCell.getRowIndex(),
                currentCell.getColumnIndex(),
                currentCell.getColumnIndex())};
        formatting.addConditionalFormatting(addresses, rule);
    }

    private void initForthCellBuy(Row lastRow, PriceInfo prices, CellStyle style, int cellIndex, int lastRowNum) {
        String formula;
        Cell currentCell;
        formula = "B" + lastRowNum + '*' + prices.getTwentyPrice()
                + "+C" + lastRowNum + '*' + prices.getTwelvePrice();

        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        if (!currentCell.getCellTypeEnum().equals(CellType.FORMULA)) {
            currentCell.setCellFormula(null);
            currentCell.setCellType(CellType.FORMULA);
        }
        currentCell.setCellFormula(formula);
        currentCell.setCellStyle(style);
    }

    private void initIntCellBuy(Row lastRow, CellStyle style, int cellIndex, int amount) {
        Cell currentCell;
        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        currentCell.setCellType(CellType.NUMERIC);
        currentCell.setCellValue(amount);
        currentCell.setCellStyle(style);
    }

    private void initFirstCellBuy(Row lastRow, BuyInfo info, CellStyle style, int cellIndex) {
        Cell currentCell;
        currentCell = (lastRow.getCell(cellIndex) == null) ? lastRow.createCell(cellIndex) : lastRow.getCell(cellIndex);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(info.getDate());
        currentCell.setCellType(CellType.STRING);
        currentCell.setCellValue(format);
        currentCell.setCellStyle(style);
    }

    /**
     * Checks if the given rowNum corresponds to the first row (row 4).
     *
     * @param rowNum Number of the row.
     * @return true if the row is the first after the titles, false otherwise.
     */
    private boolean isFirstRow(int rowNum) {
        return (rowNum <= 4);
    }


    @Override
    public void initBuyTitles(Row row, CellStyle style) {
        row.setHeightInPoints(40);

        String[] titles = {"Fecha", "20", "12", "Total", "Pago", "Saldo",
                "Envases devueltos 20", "Envases 20", "Envases devueltos 12",
                "Envases 12", "Envases totales"};

        for (int index = 0; index < titles.length; index++) {
            Cell cell = row.createCell(index);
            cell.setCellValue(titles[index]);
            cell.setCellStyle(style);
        }
    }
}
