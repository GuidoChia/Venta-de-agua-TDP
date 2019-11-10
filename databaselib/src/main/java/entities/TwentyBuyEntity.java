package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twenty_buys")
public class TwentyBuyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int twentyId;

    public int twentyBoughtAmount;

    public int twentyReturnedAmount;

    public int twentyPrice;
}
