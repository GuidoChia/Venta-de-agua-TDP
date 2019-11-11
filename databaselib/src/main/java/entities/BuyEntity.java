package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(tableName = "buys", primaryKeys = {"customerName", "buyDate"})
public class BuyEntity {
    public BuyEntity(@NonNull String customerName, @NonNull Date buyDate, long twelveId, long twentyId, long extraBuyId, double buyPaid) {
        this.customerName = customerName;
        this.buyDate = buyDate;
        this.twelveId = twelveId;
        this.twentyId = twentyId;
        this.extraBuyId = extraBuyId;
        this.buyPaid = buyPaid;
    }

    @NonNull
    @ForeignKey(entity = CustomerEntity.class, parentColumns = "customerName", childColumns = "customerName")
    private String customerName;

    @NonNull
    private Date buyDate;

    @ForeignKey(entity = TwelveBuyEntity.class, parentColumns = "twelveId", childColumns = "twelveId")
    private long twelveId;

    @ForeignKey(entity = TwentyBuyEntity.class, parentColumns = "twentyId", childColumns = "twentyId")
    private long twentyId;

    @ForeignKey(entity = ExtraBuyEntity.class, parentColumns = "extraBuyId", childColumns = "extraBuyId")
    private long extraBuyId;

    private double buyPaid;

    @NonNull
    public String getCustomerName() {
        return customerName;
    }

    public long getTwelveId() {
        return twelveId;
    }

    public long getTwentyId() {
        return twentyId;
    }

    public long getExtraBuyId() {
        return extraBuyId;
    }

    @NonNull
    public Date getBuyDate() {
        return buyDate;
    }

    public double getBuyPaid() {
        return buyPaid;
    }

    public void setCustomerName(@NonNull String customerName) {
        this.customerName = customerName;
    }

    public void setTwelveId(long twelveId) {
        this.twelveId = twelveId;
    }

    public void setTwentyId(long twentyId) {
        this.twentyId = twentyId;
    }

    public void setExtraBuyId(long extraBuyId) {
        this.extraBuyId = extraBuyId;
    }

    public void setBuyDate(@NonNull Date buyDate) {
        this.buyDate = buyDate;
    }

    public void setBuyPaid(double buyPaid) {
        this.buyPaid = buyPaid;
    }
}
