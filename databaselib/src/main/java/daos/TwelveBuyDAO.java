package daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import entities.TwelveBuyEntity;

@Dao
public interface TwelveBuyDAO {
    /*@Query("INSERT INTO twelve_buys(twelveBoughtAmount, twelveReturnedAmount, twelvePrice) " + "VALUES(:twelveBoughtAmount, :twelveReturnedAmount, :twelvePriceAmount)")
    public abstract long insertTwelveBuy(int twelveBoughtAmount, int twelveReturnedAmount, int twelvePriceAmount);*/

    @Insert
    public long insertTwelveBuy(TwelveBuyEntity entity);

    @Update
    public void update(TwelveBuyEntity entity);

    @Query("SELECT * FROM twelve_buys WHERE twelveId = :twelveBuyId")
    public TwelveBuyEntity getTwelveBuy(long twelveBuyId);

    @Query("SELECT * FROM twelve_buys")
    public abstract List<entities.TwelveBuyEntity> getAllTwelveBuys();

    @Query("SELECT count(twelveId) FROM twelve_buys GROUP BY twelveId")
    public abstract int countTwelveBuys();

    @Query("SELECT count(twelveId) FROM twelve_buys NATURAL JOIN buys " + "WHERE buyDate BETWEEN :date1 AND :date2 " + "GROUP BY twelveId")
    public abstract int countTwelveBuysBetween(Date date1, Date date2);
}
