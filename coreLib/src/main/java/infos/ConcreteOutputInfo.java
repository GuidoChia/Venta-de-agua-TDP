package infos;

import java.util.Date;

public class ConcreteOutputInfo implements OutputInfo {
    private Date lastDate;
    private double balance;
    private int twentyBalance;
    private int twelveBalance;

    /**
     * Creates a new instance of ConcreteOutputInfo
     * @param lastDate The last buy of the customer
     * @param balance The money balance of the customer
     * @param twentyBalance The twenty canisters balance of the customer
     * @param twelveBalance The twelve canisters balance of the customer
     */
    public ConcreteOutputInfo(Date lastDate, double balance, int twentyBalance, int twelveBalance) {
        this.lastDate = (Date) lastDate.clone();
        this.balance = balance;
        this.twelveBalance = twelveBalance;
        this.twentyBalance = twentyBalance;
    }

    @Override
    public Date getLastDate() {
        return (Date) lastDate.clone();
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
        return twelveBalance + twentyBalance;
    }
}
