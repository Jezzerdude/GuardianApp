package com.example.guardapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.guardapp.model.ArticleWithBody;

@Database(entities = ArticleWithBody.class, version = 1)
public abstract class GuardDatabase extends RoomDatabase {
    private static GuardDatabase INSTANCE;

    public static GuardDatabase init(Context context) {
        if (INSTANCE != null) {
            throw new RuntimeException("Database Already initialized use getInstance.");
        } else {
            INSTANCE = Room.databaseBuilder(context,
                    GuardDatabase.class,
                    "guardian_scope_database").build();
            return INSTANCE;
        }

    }

    public static GuardDatabase getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            throw new RuntimeException("You can't getInstance before Initializing the Database with init.");
        }
    }


}

