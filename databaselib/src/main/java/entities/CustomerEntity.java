package entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class CustomerEntity {
    @NonNull
    @PrimaryKey
    private String customerName;

    @ColumnInfo(defaultValue = "0")
    private int customerBalance;

    @ColumnInfo(defaultValue = "0")
    private int customerTwentyBalance;

    @ColumnInfo(defaultValue = "0")
    private int customerTwelveBalance;

    @NonNull
    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerBalance() {
        return customerBalance;
    }

    public int getCustomerTwentyBalance() {
        return customerTwentyBalance;
    }

    public int getCustomerTwelveBalance() {
        return customerTwelveBalance;
    }

    public void setCustomerName(@NonNull String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerBalance(int customerBalance) {
        this.customerBalance = customerBalance;
    }

    public void setCustomerTwentyBalance(int customerTwentyBalance) {
        this.customerTwentyBalance = customerTwentyBalance;
    }

    public void setCustomerTwelveBalance(int customerTwelveBalance) {
        this.customerTwelveBalance = customerTwelveBalance;
    }
}
