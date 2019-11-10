package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(tableName = "buys", primaryKeys = {"customerName", "buyDate"})
public class BuyEntity {
    @NonNull
    @ForeignKey(entity = CustomerEntity.class, parentColumns = "customerName", childColumns = "customerName")
    private String customerName;

    @NonNull
    private Date buyDate;

    @ForeignKey(entity = TwelveBuyEntity.class, parentColumns = "twelveId", childColumns = "twelveId")
    private int twelveId;

    @ForeignKey(entity = TwentyBuyEntity.class, parentColumns = "twentyId", childColumns = "twentyId")
    private int twentyId;

    @ForeignKey(entity = ExtraBuyEntity.class, parentColumns = "extraBuyId", childColumns = "extraBuyId")
    private int extraBuyId;

    private int buyPaid;

    @NonNull
    public String getCustomerName() {
        return customerName;
    }

    public int getTwelveId() {
        return twelveId;
    }

    public int getTwentyId() {
        return twentyId;
    }

    public int getExtraBuyId() {
        return extraBuyId;
    }

    @NonNull
    public Date getBuyDate() {
        return buyDate;
    }

    public int getBuyPaid() {
        return buyPaid;
    }

    public void setCustomerName(@NonNull String customerName) {
        this.customerName = customerName;
    }

    public void setTwelveId(int twelveId) {
        this.twelveId = twelveId;
    }

    public void setTwentyId(int twentyId) {
        this.twentyId = twentyId;
    }

    public void setExtraBuyId(int extraBuyId) {
        this.extraBuyId = extraBuyId;
    }

    public void setBuyDate(@NonNull Date buyDate) {
        this.buyDate = buyDate;
    }

    public void setBuyPaid(int buyPaid) {
        this.buyPaid = buyPaid;
    }
}
