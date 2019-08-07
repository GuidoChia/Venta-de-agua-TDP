package infos;

import java.util.Date;

public class ConcreteOutputInfo implements OutputInfo {
    private Date lastDate;
    private double balance;
    private int twentyBalance;
    private int twelveBalance;
    private int twentyBought;
    private int twelveBought;
    private String description;

    /**
     * Creates a new instance of ConcreteOutputInfo
     *
     * @param lastDate      The last buy of the customer
     * @param balance       The money balance of the customer
     * @param twentyBalance The twenty canisters balance of the customer
     * @param twelveBalance The twelve canisters balance of the customer
     * @param twentyBought  The amount of twenty canisters bought
     * @param twelveBought  The amount of twelve canisters bought
     * @param description   Descpription of the buy
     */
    public ConcreteOutputInfo(Date lastDate, double balance, int twentyBalance, int twelveBalance, int twentyBought, int twelveBought, String description) {
        this.lastDate = (Date) lastDate.clone();
        this.balance = balance;
        this.twelveBalance = twelveBalance;
        this.twentyBalance = twentyBalance;
        this.twelveBought = twelveBought;
        this.twentyBought = twentyBought;
        this.description = description;
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
    public int getTwentyBought() {
        return twentyBought;
    }

    @Override
    public int getTwelveBought() {
        return twelveBought;
    }

    @Override
    public int getCanistersBalance() {
        return twelveBalance + twentyBalance;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
