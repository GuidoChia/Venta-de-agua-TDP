package database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseBuilderHelper {
    private static YporaDatabase INSTANCE;

    public static YporaDatabase getDatabase(Context c) {
        if (INSTANCE == null) {
            return Room.databaseBuilder(c, YporaDatabase.class, "ypora_db").allowMainThreadQueries().build();
        } else return INSTANCE;
    }
}
