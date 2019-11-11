package daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import entities.ExtraBuyEntity;

@Dao
public interface ExtraBuyDAO {
    /*@Query("INSERT INTO extra_buys(extraBuyPrice, extraBuyDescription) " + "VALUES(:extraBuyPrice, :extraBuyDescription)")
    public abstract long insertExtraBuy(int extraBuyPrice, String extraBuyDescription);*/

    @Insert
    public long insertExtraBuy(ExtraBuyEntity entity);

    @Update
    public void update(ExtraBuyEntity entity);

    @Query("SELECT * FROM extra_buys WHERE extraBuyId=:extraBuyId")
    public ExtraBuyEntity getExtraBuy(long extraBuyId);

    @Query("SELECT * FROM extra_buys")
    public abstract List<ExtraBuyEntity> getAllExtraBuys();

    @Query("SELECT count(extraBuyId) FROM extra_buys GROUP BY extraBuyId")
    public abstract int countExtraBuys();

    @Query("SELECT count(extraBuyId) FROM extra_buys NATURAL JOIN buys " + "WHERE buyDate BETWEEN :date1 AND :date2 " + "GROUP BY extraBuyId")
    public abstract int countExtraBuysBetween(Date date1, Date date2);

}
