package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity (tableName = "buys", primaryKeys = {"customerName", "buyDate"})
public class BuyEntity {
    @NonNull
    @ForeignKey(entity = CustomerEntity.class, parentColumns = "customerName", childColumns = "customerName")
    public String customerName;

    @ForeignKey(entity = TwelveBuyEntity.class, parentColumns = "twelveId", childColumns = "twelveId")
    public int twelveId;

    @ForeignKey(entity = TwentyBuyEntity.class, parentColumns = "twentyId", childColumns = "twentyId")
    public int twentyId;

    @ForeignKey(entity = ExtraBuyEntity.class, parentColumns = "extraBuyId", childColumns = "extraBuyId")
    public int extraBuyId;

    @NonNull
    public String buyDate;

    public int buyPaid;



}
