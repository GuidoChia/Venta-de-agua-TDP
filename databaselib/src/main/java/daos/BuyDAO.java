package daos;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
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
    public abstract BuyEntity getBuy(String customerName, Date buyDate);

    @Query("SELECT * FROM buys WHERE customerName=:customerName ORDER BY buyDate DESC LIMIT 1")
    public abstract BuyEntity getLastBuy(String customerName);

    @Delete
    public abstract int deleteBuyOnly(@NonNull BuyEntity entity);

    @Transaction
    public long insertBuy(@NonNull String customerName, @NonNull Date buyDate, TwelveBuyEntity twelveBuyEntity, TwentyBuyEntity twentyBuyEntity, ExtraBuyEntity extraBuyEntity, double buyPaid) {
        BuyEntity entity;
        long twelveId, twentyId, extraBuyId;

        double twelveBuyBalance = 0, twentyBuyBalance = 0, extraBuyBalance = 0, totalBalance = 0;
        int twelveCanistersBalance = 0, twentyCanistersBalance = 0;

        CustomerDAO customerDAO = databaseInstance.getCustomerDAO();
        customerDAO.insert(customerName);

        entity = getBuy(customerName, buyDate);

        if (entity == null) {
            entity = new BuyEntity(customerName, buyDate, 0, 0, 0, 0);
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

            twelveBuyBalance = twelveBuyEntity.getTwelveBoughtAmount() * twelveBuyEntity.getTwelvePrice();
            twelveCanistersBalance = twelveBuyEntity.getTwelveBoughtAmount() - twelveBuyEntity.getTwelveReturnedAmount();
        }

        if (twentyBuyEntity != null) {
            TwentyBuyDAO twentyBuyDAO = databaseInstance.getTwentyBuyDAO();
            if (entity.getTwentyId() == 0) {
                twentyId = twentyBuyDAO.insertTwentyBuy(twentyBuyEntity);
                entity.setTwentyId(twentyId);
            } else {
                TwentyBuyEntity originalTwentyBuy = twentyBuyDAO.getTwentyBuy(entity.getTwentyId());
                originalTwentyBuy.setTwentyReturnedAmount(twentyBuyEntity.getTwentyReturnedAmount() + originalTwentyBuy.getTwentyReturnedAmount());
                originalTwentyBuy.setTwentyBoughtAmount(twentyBuyEntity.getTwentyBoughtAmount() + originalTwentyBuy.getTwentyBoughtAmount());

                twentyBuyDAO.update(originalTwentyBuy);
            }

            twentyBuyBalance = twentyBuyEntity.getTwentyBoughtAmount() * twentyBuyEntity.getTwentyPrice();
            twentyCanistersBalance = twentyBuyEntity.getTwentyBoughtAmount() - twentyBuyEntity.getTwentyReturnedAmount();
        }

        if (extraBuyEntity != null) {
            ExtraBuyDAO extraBuyDAO = databaseInstance.getExtraBuyDAO();
            if (entity.getExtraBuyId() == 0) {
                extraBuyId = extraBuyDAO.insertExtraBuy(extraBuyEntity);
                entity.setExtraBuyId(extraBuyId);
            } else {
                ExtraBuyEntity originalExtraBuy = extraBuyDAO.getExtraBuy(entity.getExtraBuyId());
                originalExtraBuy.setExtraBuyPrice(originalExtraBuy.getExtraBuyPrice() + extraBuyEntity.getExtraBuyPrice());
                originalExtraBuy.setExtraBuyDescription(originalExtraBuy.getExtraBuyDescription() + ". " + extraBuyEntity.getExtraBuyDescription());

                extraBuyDAO.update(originalExtraBuy);
            }
            extraBuyBalance = extraBuyEntity.getExtraBuyPrice();
        }

        entity.setBuyPaid(entity.getBuyPaid() + buyPaid);

        totalBalance = twelveBuyBalance + twentyBuyBalance + extraBuyBalance;
        customerDAO.updateBalances(customerName, totalBalance - buyPaid, twentyCanistersBalance, twelveCanistersBalance);

        return insertBuy(entity);

    }

    @Transaction
    public long delete(@NonNull BuyEntity entity) {
        double twelveBuyBalance = 0, twentyBuyBalance = 0, extraBuyBalance = 0, totalBalance = 0;
        int twelveCanistersBalance = 0, twentyCanistersBalance = 0;

        if (entity.getExtraBuyId() > 0) {
            ExtraBuyDAO extraBuyDAO = databaseInstance.getExtraBuyDAO();
            ExtraBuyEntity extraBuyEntity = extraBuyDAO.getExtraBuy(entity.getExtraBuyId());
            extraBuyBalance = extraBuyEntity.getExtraBuyPrice();
            extraBuyDAO.delete(extraBuyEntity);
        }

        if (entity.getTwentyId() > 0) {
            TwentyBuyDAO twentyBuyDAO = databaseInstance.getTwentyBuyDAO();
            TwentyBuyEntity twentyBuyEntity = twentyBuyDAO.getTwentyBuy(entity.getTwentyId());
            twentyBuyBalance = twentyBuyEntity.getTwentyBoughtAmount() * twentyBuyEntity.getTwentyPrice();
            twentyCanistersBalance = twentyBuyEntity.getTwentyBoughtAmount() - twentyBuyEntity.getTwentyReturnedAmount();

            twentyBuyDAO.delete(twentyBuyEntity);
        }

        if (entity.getTwelveId() > 0) {
            TwelveBuyDAO twelveBuyDAO = databaseInstance.getTwelveBuyDAO();
            TwelveBuyEntity twelveBuyEntity = twelveBuyDAO.getTwelveBuy(entity.getTwelveId());
            twelveBuyBalance = twelveBuyEntity.getTwelveBoughtAmount() * twelveBuyEntity.getTwelvePrice();
            twelveCanistersBalance = twelveBuyEntity.getTwelveBoughtAmount() - twelveBuyEntity.getTwelveReturnedAmount();
            twelveBuyDAO.delete(twelveBuyEntity);
        }

        totalBalance = twelveBuyBalance + twentyBuyBalance + extraBuyBalance;
        CustomerDAO customerDAO = databaseInstance.getCustomerDAO();
        customerDAO.updateBalances(entity.getCustomerName(), -(totalBalance - entity.getBuyPaid()), -twentyCanistersBalance, -twelveCanistersBalance);

        return deleteBuyOnly(entity);
    }
}
