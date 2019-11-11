package daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import entities.TwentyBuyEntity;

@Dao
public interface TwentyBuyDAO {
    /*@Query("INSERT INTO twenty_buys(twentyBoughtAmount, twentyReturnedAmount, twentyPrice) " + "VALUES(:twentyBoughtAmount, :twentyReturnedAmount, :twentyPriceAmount)")
    public abstract long insertTwentyBuy(int twentyBoughtAmount, int twentyReturnedAmount, int twentyPriceAmount);*/

    @Insert
    public long insertTwentyBuy(TwentyBuyEntity entity);

    @Update
    void update(TwentyBuyEntity entity);

    @Query("SELECT * FROM twenty_buys WHERE twentyId=:twentyId")
    public TwentyBuyEntity getTwentyBuy(long twentyId);

    @Query("SELECT * FROM twenty_buys")
    public abstract List<TwentyBuyEntity> getAllTwentyBuys();

    @Query("SELECT count(twentyId) FROM twenty_buys GROUP BY twentyId")
    public abstract int countTwentyBuys();

    @Query("SELECT count(twentyId) FROM twenty_buys NATURAL JOIN buys " + "WHERE buyDate BETWEEN :date1 AND :date2 " + "GROUP BY twentyId")
    public abstract int countTwentyBuysBetween(Date date1, Date date2);

    @Query("DELETE FROM twenty_buys WHERE twentyId=:twentyId")
    public abstract int delete(long twentyId);

    @Delete
    public abstract int delete(TwentyBuyEntity entity);
}
