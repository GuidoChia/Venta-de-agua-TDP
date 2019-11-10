package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import converter.DateConverter;
import daos.CustomerDAO;
import entities.CustomerEntity;

@Database(entities = {CustomerEntity.class}, version = 1)
@TypeConverters(value = {DateConverter.class})
public abstract class YporaDatabase extends RoomDatabase {
    public abstract CustomerDAO getCustomerDAO();
}
