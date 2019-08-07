package infos;

import java.util.Date;

public class ConcreteExtraBuyInfo implements ExtraBuyInfo {
    private double paid;
    private double price;
    private String name;
    private String description;
    private Date date;

    public ConcreteExtraBuyInfo(double paid, double price, String description, String name, Date date) {
        this.paid = paid;
        this.price = price;
        this.description = description;
        this.name = name;
        this.date = date;
    }

    @Override
    public double getPaid() {
        return paid;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
