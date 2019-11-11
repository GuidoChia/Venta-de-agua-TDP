package daos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

import database.YporaDatabase;
import entities.BuyEntity;
import entities.ExtraBuyEntity;
import entities.TwelveBuyEntity;
import entities.TwentyBuyEntity;

@Dao
public abstract class BuyDAO {
    YporaDatabase databaseInstance;

    public BuyDAO(YporaDatabase yporaDatabase) {
        this.databaseInstance = yporaDatabase;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertBuy(BuyEntity entity);

    @Query("SELECT * FROM buys")
    public abstract List<BuyEntity> getAll();

    @Query("SELECT * FROM buys WHERE customerName =:customerName")
    public abstract List<BuyEntity> getBuysFromCustomer(String customerName);

    @Query("SELECT * FROM buys WHERE customerName=:customerName AND buyDate=:buyDate")
    public abstract List<BuyEntity> getBuy(String customerName, Date buyDate);


    @Transaction
    public long insertBuy(@NonNull String customerName, @NonNull Date buyDate, TwelveBuyEntity twelveBuyEntity, TwentyBuyEntity twentyBuyEntity, ExtraBuyEntity extraBuyEntity, double buyPaid) {
        BuyEntity entity;
        long twelveId, twentyId, extraBuyId;
        twelveId = twentyId = extraBuyId = 0;

        databaseInstance.getCustomerDAO().insert(customerName);

        List<BuyEntity> entityList = getBuy(customerName, buyDate);

        Log.d("Ypora", "SIZE ENTITY: " + entityList.size());

        if (entityList.size() == 0) {
            entity = new BuyEntity(customerName, buyDate, 0, 0, 0, 0);
        } else {
            entity = entityList.get(0);
        }

        if (twelveBuyEntity != null) {
            TwelveBuyDAO twelveBuyDAO = databaseInstance.getTwelveBuyDAO();
            if (entity.getTwelveId() == 0) {
                twelveId = twelveBuyDAO.insertTwelveBuy(twelveBuyEntity);
                entity.setTwelveId(twelveId);
            } else {
                TwelveBuyEntity originalTwelveBuy = twelveBuyDAO.getTwelveBuy(entity.getTwelveId());
                originalTwelveBuy.setTwelveReturnedAmount(twelveBuyEntity.getTwelveReturnedAmount() + originalTwelveBuy.getTwelveReturnedAmount());
                originalTwelveBuy.setTwelveBoughtAmount(twelveBuyEntity.getTwelveBoughtAmount() + originalTwelveBuy.getTwelveBoughtAmount());

                twelveBuyDAO.update(originalTwelveBuy);
            }
        }

        if (twentyBuyEntity != null) {
            if (entity.getTwentyId() == 0) {
                twentyId = databaseInstance.getTwentyBuyDAO().insertTwentyBuy(twentyBuyEntity);
                entity.setTwentyId(twentyId);
            } else {
                //TODO actualizar la compra sumandole lo nuevo
            }
        }

        if (extraBuyEntity != null) {
            if (entity.getExtraBuyId() == 0) {
                extraBuyId = databaseInstance.getExtraBuyDAO().insertExtraBuy(extraBuyEntity);
            } else {
                //TODO actualizar la compra sumandole lo nuevo
            }
        }

        entity.setBuyPaid(entity.getBuyPaid() + buyPaid);


        return insertBuy(entity);

    }
}
