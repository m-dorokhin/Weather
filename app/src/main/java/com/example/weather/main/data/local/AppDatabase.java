package com.example.weather.main.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {City.class, Settings.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CitiesDao citiesDao();

    public abstract SettingsDao settingsDao();
}
