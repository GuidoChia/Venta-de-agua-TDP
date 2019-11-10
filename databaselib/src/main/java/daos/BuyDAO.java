package daos;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import entities.BuyEntity;

@Dao
public interface BuyDAO {
    @Query("INSERT INTO buys(customerName, buyDate, buyPaid) VALUES(:customerName, :buyDate, :buyPaid)")
    void insert(String customerName, String buyDate, int buyPaid);

    @Query("SELECT * FROM buys")
    List<BuyEntity> getAll();

    @Query("SELECT * FROM buys WHERE customerName =:customerName")
    List<BuyEntity> getBuysFromCustomer(String customerName);
}
