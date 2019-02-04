package infos;

public class ConcretePriceInfo implements PriceInfo {

    private double twelvePrice,
            twentyPrice;

    /**
     * Creates an instance of ConcretePriceInfo
     *
     * @param twelvePrice Price of twelve canisters
     * @param twentyPrice Price of twenty canisters
     */
    public ConcretePriceInfo(double twelvePrice, double twentyPrice) {
        this.twelvePrice = twelvePrice;
        this.twentyPrice = twentyPrice;
    }

    @Override
    public double getTwelvePrice() {
        return twelvePrice;
    }

    @Override
    public double getTwentyPrice() {
        return twentyPrice;
    }
}
