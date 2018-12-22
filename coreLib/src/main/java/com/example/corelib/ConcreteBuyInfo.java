package com.example.corelib;

import java.util.Date;

/**
 * Implementation of the BuyInfo interface
 * @author Guido Chia
 */
public class ConcreteBuyInfo implements BuyInfo {
    private float paid;
    private int twentyCanister;
    private int twelveCanister;
    private int returnedCanister;
    private Date date;
    private String name;

    /**
     * Creates a new instance of ConcreteBuyInfo
     * @param paid Amount of money paid
     * @param twenty Amount of twenty canisters bought
     * @param twelve Amount of twelve canisters bought
     * @param returned Amount of returned canisters
     * @param date Date of the buy
     * @param name Name of the customer
     */

    public BuyInfo(float paid, int twenty, int twelve, int returned, Date date, String name){
        this.paid=paid;
        this.twentyCanister=twenty;
        this.twelveCanister=twelve;
        this.returnedCanister=returned;
        this.date=date;
        this.name=name;
    }


    @Override
    public float getPaid() {
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
    public int getReturnedCanister() {
        return returnedCanister;
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
