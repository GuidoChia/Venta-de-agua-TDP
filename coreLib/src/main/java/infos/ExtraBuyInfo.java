package infos;

import java.util.Date;

public interface ExtraBuyInfo {
    /**
     * Paid getter.
     *
     * @return Amount of money paid.
     */
    double getPaid();

    /**
     * Price getter.
     *
     * @return Price of the total buy.
     */
    double getPrice();

    /**
     * Description getter.
     *
     * @return Description of the buy.
     */
    String getDescription();

    /**
     * Name getter.
     *
     * @return Name of the customer.
     */
    String getName();

    /**
     * Date getter.
     *
     * @return Date of the buy
     */
    Date getDate();
}
