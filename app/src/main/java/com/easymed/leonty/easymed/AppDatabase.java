package com.easymed.leonty.easymed;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Pacient.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PacientDao pacientDao();
}
