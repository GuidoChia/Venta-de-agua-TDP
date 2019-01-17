package infos;

import java.util.Date;

/**
 * Implementation of the BuyInfo interface
 *
 * @author Guido Chia
 */
public class ConcreteBuyInfo implements BuyInfo {
    private float paid;
    private int twentyCanister;
    private int twelveCanister;
    private int twentyReturned;
    private int twelveReturned;
    private Date date;
    private String name;

    /**
     * Creates a new instance of ConcreteBuyInfo
     *
     * @param paid           Amount of money paid
     * @param twenty         Amount of twenty canisters bought
     * @param twelve         Amount of twelve canisters bought
     * @param twentyReturned Amount of returned twenty canisters
     * @param twelveReturned Amount of returned twelve canisters
     * @param date           Date of the buy
     * @param name           Name of the customer
     */

    public ConcreteBuyInfo(float paid, int twenty, int twelve, int twentyReturned, int twelveReturned, Date date, String name) {
        this.paid = paid;
        this.twentyCanister = twenty;
        this.twelveCanister = twelve;
        this.twentyReturned = twentyReturned;
        this.twelveReturned = twelveReturned;
        this.date = date;
        this.name = name;
    }


    @Override
    public double getPaid() {
        return paid;
    }

    @Override
    public int getTwentyCanister() {
        return twentyCanister;
    }

    @Override
    public int getTwelveCanister() {
        return twelveCanister;
    }

    @Override
    public int getTwentyReturnedCanister() {
        return twentyReturned;
    }

    @Override
    public int getTwelveReturnedCanister() {
        return twelveReturned;
    }


    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getName() {
        return name;
    }
}
