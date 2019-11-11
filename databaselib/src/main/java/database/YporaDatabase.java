package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import converter.DateConverter;
import daos.BuyDAO;
import daos.CustomerDAO;
import daos.ExtraBuyDAO;
import daos.TwelveBuyDAO;
import daos.TwentyBuyDAO;
import entities.BuyEntity;
import entities.CustomerEntity;
import entities.ExtraBuyEntity;
import entities.TwelveBuyEntity;
import entities.TwentyBuyEntity;

@Database(entities = {CustomerEntity.class, BuyEntity.class, ExtraBuyEntity.class, TwentyBuyEntity.class, TwelveBuyEntity.class}, version = 1)
@TypeConverters(value = {DateConverter.class})
public abstract class YporaDatabase extends RoomDatabase {
    public abstract CustomerDAO getCustomerDAO();

    public abstract BuyDAO getBuyDAO();

    public abstract TwentyBuyDAO getTwentyBuyDAO();

    public abstract TwelveBuyDAO getTwelveBuyDAO();

    public abstract ExtraBuyDAO getExtraBuyDAO();

}
