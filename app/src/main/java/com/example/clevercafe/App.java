package com.example.clevercafe;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.clevercafe.db.AppDatabase;

/**
 * Created by Chudofom on 24.07.17.
 */

public class App extends Application {
    public static AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "smartcafe").build();
    }
}
