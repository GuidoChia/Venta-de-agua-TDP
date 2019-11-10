package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import daos.CustomerDAO;
import entities.CustomerEntity;

@Database(entities = {CustomerEntity.class}, version = 1)
public abstract class YporaDatabase extends RoomDatabase {
    public abstract CustomerDAO getCustomerDAO();
}
