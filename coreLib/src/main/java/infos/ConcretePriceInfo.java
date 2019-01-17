package infos;

public class ConcretePriceInfo implements PriceInfo {

    private double twelvePrice,
                    twentyPrice;

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
