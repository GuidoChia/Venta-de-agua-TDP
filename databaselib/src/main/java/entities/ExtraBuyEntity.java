package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "extra_buys")
public class ExtraBuyEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int extraBuyId;

    public String extraBuyDescription;

    public int extra_buy_price;
}
