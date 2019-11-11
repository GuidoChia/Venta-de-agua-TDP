package daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entities.CustomerEntity;

@Dao
public interface CustomerDAO {
    @Query("INSERT OR IGNORE INTO customers(customerName) VALUES(:customerName)")
    void insert(String customerName);


    @Insert
    void insert(CustomerEntity customer);

    @Query("UPDATE customers SET customerBalance = :balance+customerBalance, " +
            "customerTwentyBalance=:twentyBalance+customerTwentyBalance, " +
            "customerTwelveBalance=:twelveBalance+customerTwelveBalance " + "WHERE customerName =" +
            " :customerName")
    void updateBalances(String customerName, double balance, int twentyBalance, int twelveBalance);


    @Query("SELECT customerName FROM customers")
    List<String> getCustomerNames();

    @Query("SELECT * FROM customers")
    List<CustomerEntity> getAll();


}
