package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twelve_buys")
public class TwelveBuyEntity {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    public int twelveId;

    public int twelveBoughtAmount;

    public int twelveReturnedAmount;

    public int twelvePrice;
}
