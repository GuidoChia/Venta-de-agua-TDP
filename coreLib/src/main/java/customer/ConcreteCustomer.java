package customer;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import infos.CustomerInfo;
import visitors.Visitor;

/**
 * Implementation of the Customer interface
 * It represents the customer in a month.
 *
 * @author Guido Chia
 */
public class ConcreteCustomer implements Customer {

    private CustomerInfo info;
    private List<Date> dates;
    private double balance;
    private String name;

    public ConcreteCustomer(String name) {
        dates = new LinkedList<>();
        this.name = name;
    }

    @Override
    public Collection<Date> getLastDates() {
        return dates;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getMonthPaid() {
        double res = 0;

        if (info != null) {
            res = info.paid();
        }

        return res;
    }

    @Override
    public int getTwentyBought() {
        int res = 0;

        if (info != null) {
            res = info.twentyCanisters();
        }

        return res;
    }

    @Override
    public int getTwelveBought() {
        int res = 0;

        if (info != null) {
            res = info.twelveCanisters();
        }

        return res;
    }



    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void addUpdate(Date date, int twelveAmount, int twentyAmount, double paid) {
        dates.add(date);
        if (info == null) {
            info = new CustomerInfo(twelveAmount, twentyAmount, paid);
        } else {
            info.addTwentyCansiters(twentyAmount);
            info.addTwelveCanisters(twelveAmount);
            info.addPaid(paid);
        }
    }

    @Override
    public boolean isEmpty() {

        return (info == null) && dates.isEmpty() && (balance == 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


}
