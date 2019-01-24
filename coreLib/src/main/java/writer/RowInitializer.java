package writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import customer.Customer;
import infos.BuyInfo;
import infos.PriceInfo;

public interface RowInitializer {
    /**
     * Initializes the route titles
     * @param routeSheet Sheet of the route
     * @param style Style to use
     */
    void initRouteTitles(Sheet routeSheet, CellStyle style);

    /**
     * Initializes a row of the route
     * @param row Row to initialize
     * @param customer Customer to write on the row
     * @param style Style to use
     */
    void initRouteRow(Row row, Customer customer, CellStyle style);

    /**
     * Initializes a row with a buy
     * @param row Row to initialize
     * @param info Info to write in the row
     * @param prices Prices to use in formulas
     * @param style Style to use
     */
    void initBuyRow(Row row, BuyInfo info, PriceInfo prices, CellStyle style);

    /**
     * Initializes a the buy titles
     * @param row The row to initialize
     * @param style Style to use
     */
    void initBuyTitles(Row row, CellStyle style);
}