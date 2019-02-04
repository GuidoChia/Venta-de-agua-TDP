package infos;

/**
 * Class that represents the customer info of a given time interval.
 *
 * @author Guido Chia
 */
public class CustomerInfo {

    private double paid;
    private int twelve;
    private int twenty;

    /**
     * Creates an instance of CustomerInfo
     *
     * @param twelve Amount of twelve canisters
     * @param twenty Amount of twenty canisters
     * @param paid   Amount of money paid
     */
    public CustomerInfo(int twelve, int twenty, double paid) {
        this.paid = paid;
        this.twelve = twelve;
        this.twenty = twenty;
    }

    public double paid() {
        return paid;
    }

    public int twelveCanisters() {
        return twelve;
    }

    public int twentyCanisters() {
        return twenty;
    }

    public void addPaid(double paid) {
        this.paid += paid;
    }

    public void addTwelveCanisters(int twelve) {
        this.twelve += twelve;
    }

    public void addTwentyCanisters(int twenty) {
        this.twenty += twenty;
    }

    @Override
    public String toString() {
        String first,
                second,
                third;

        first = (twelve > 0) ? "Bidones de 12: " + twelve + ". " : "";
        second = (twenty > 0) ? "Bidones de 20: " + twenty + ". " : "";
        third = "Pago: " + ((int) paid) + ". ";

        return first + second + third;

    }
}
