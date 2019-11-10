package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twenty_buys")
public class TwentyBuyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int twentyId;

    private int twentyBoughtAmount;

    private int twentyReturnedAmount;

    private int twentyPrice;

    public int getTwentyId() {
        return twentyId;
    }

    public int getTwentyBoughtAmount() {
        return twentyBoughtAmount;
    }

    public int getTwentyReturnedAmount() {
        return twentyReturnedAmount;
    }

    public int getTwentyPrice() {
        return twentyPrice;
    }
}
