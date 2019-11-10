package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twelve_buys")
public class TwelveBuyEntity {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int twelveId;

    private int twelveBoughtAmount;

    private int twelveReturnedAmount;

    private int twelvePrice;

    public int getTwelveId() {
        return twelveId;
    }

    public int getTwelveBoughtAmount() {
        return twelveBoughtAmount;
    }

    public int getTwelveReturnedAmount() {
        return twelveReturnedAmount;
    }

    public int getTwelvePrice() {
        return twelvePrice;
    }

    public void setTwelveId(int twelveId) {
        this.twelveId = twelveId;
    }

    public void setTwelveBoughtAmount(int twelveBoughtAmount) {
        this.twelveBoughtAmount = twelveBoughtAmount;
    }

    public void setTwelveReturnedAmount(int twelveReturnedAmount) {
        this.twelveReturnedAmount = twelveReturnedAmount;
    }

    public void setTwelvePrice(int twelvePrice) {
        this.twelvePrice = twelvePrice;
    }
}
