package entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "customers")
public class CustomerEntity {
    @NonNull
    @PrimaryKey
    public String customerName;

    @ColumnInfo(defaultValue = "0")
    public int customerBalance;

    @ColumnInfo(defaultValue = "0")
    public int customerTwentyBalance;

    @ColumnInfo(defaultValue = "0")
    public int customerTwelveBalance;



}
