package daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entities.CustomerEntity;

@Dao
public interface CustomerDAO {
    @Query("INSERT INTO customers(customerName) VALUES(:customerName)")
    void insert(String customerName);

    @Insert
    void insert(CustomerEntity customer);

    @Query("UPDATE customers SET customerBalance = :balance+customerBalance WHERE customerName = :customerName")
    void updateBalance(int balance, String customerName);

    @Query("SELECT customerName FROM customers")
    List<String> getCustomerNames();

    @Query("SELECT * FROM customers")
    List<CustomerEntity> getAll();


}
