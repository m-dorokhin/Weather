package com.example.weather.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SettingsDao {
    @Insert
    void insert(Settings settings);

    @Query("SELECT * FROM settings WHERE id = 0")
    Settings get();

    @Update
    void update(Settings settings);
}
