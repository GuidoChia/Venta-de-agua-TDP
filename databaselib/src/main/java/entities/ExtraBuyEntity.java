package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "extra_buys")
public class ExtraBuyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int extraBuyId;

    private String extraBuyDescription;

    private double extraBuyPrice;

    public int getExtraBuyId() {
        return extraBuyId;
    }

    public String getExtraBuyDescription() {
        return extraBuyDescription;
    }

    public double getExtraBuyPrice() {
        return extraBuyPrice;
    }

    public void setExtraBuyId(int extraBuyId) {
        this.extraBuyId = extraBuyId;
    }

    public void setExtraBuyDescription(String extraBuyDescription) {
        this.extraBuyDescription = extraBuyDescription;
    }

    public void setExtraBuyPrice(double extraBuyPrice) {
        this.extraBuyPrice = extraBuyPrice;
    }
}
