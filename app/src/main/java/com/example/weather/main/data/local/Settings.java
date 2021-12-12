package com.example.weather.main.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {
    @PrimaryKey
    public long id;
    @ColumnInfo(name = "selectCity")
    public int selectCity;
}
