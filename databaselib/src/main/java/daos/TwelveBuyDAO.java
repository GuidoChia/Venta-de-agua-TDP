package daos;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TwelveBuyDAO {
    @Query("INSERT INTO twelve_buys(twelveBoughtAmount, twelveReturnedAmount, twelvePrice) " +
            "VALUES(:twelveBoughtAmount, :twelveReturnedAmount, :twelvePriceAmount)")
    void insert(int twelveBoughtAmount, int twelveReturnedAmount, int twelvePriceAmount);


}
