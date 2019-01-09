package infos;

/**
 * Class that represents the customer info of a month.
 * @author Guido Chia
 */
public class CustomerInfo {

    private double paid;
    private int twelve;
    private int twenty;

    public CustomerInfo(int twelve, int twenty, double paid){
        this.paid=paid;
        this.twelve=twelve;
        this.twenty=twenty;
    }

    public double paid(){
        return paid;
    }

    public int twelveCanisters(){
        return twelve;
    }

    public int twentyCanisters(){
        return twenty;
    }

    public void addPaid(double paid){
        this.paid+=paid;
    }

    public void addTwelveCanisters(int twelve){
        this.twelve+=twelve;
    }

    public void addTwentyCanisters(int twenty){
        this.twenty+=twenty;
    }
}
