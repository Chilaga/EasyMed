package com.easymed.leonty.easymed;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DbBuilder {
    private Context context;

    DbBuilder(Context context) {
        this.context = context;
    }

    protected AppDatabase build() {
        return Room.databaseBuilder(context, AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
    }
}
