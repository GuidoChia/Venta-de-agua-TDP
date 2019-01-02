package com.example.corelib;

import java.util.Date;

public class ConcreteOutputInfo implements OutputInfo {
    private Date lastDate;
    private double balance;
    private int twentyBalance;
    private int twelveBalance;

    public ConcreteOutputInfo(Date lastDate, double balance, int twentyBalance, int twelveBalance){
        this.lastDate=lastDate;
        this.balance=balance;
        this.twelveBalance=twelveBalance;
        this.twentyBalance=twentyBalance;
    }

    @Override
    public Date getLastDate() {
        return lastDate;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public int getTwentyBalance() {
        return twentyBalance;
    }

    @Override
    public int getTwelveBalance() {
        return twelveBalance;
    }

    @Override
    public int getCanistersBalance() {
        return twelveBalance+twentyBalance;
    }
}
