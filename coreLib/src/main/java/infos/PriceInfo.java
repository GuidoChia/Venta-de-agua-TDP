package infos;

/**
 * The interface that represents the prices of the canisters.
 * @author Guido Chia
 */
public interface PriceInfo {
    /**
     * Getter of the twelve canister price.
     * @return Price of the twelve canister.
     */
    double getTwelvePrice();

    /**
     * Getter of the twenty canister price.
     * @return Price of the twenty canister.
     */
    double getTwentyPrice();
}
