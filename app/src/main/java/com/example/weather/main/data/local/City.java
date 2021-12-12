package com.example.weather.main.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class City {
    @PrimaryKey
    public long id;
    public String name;
    public String country;
    public double lon;
    public double lat;
    public int weight;
    @ColumnInfo(name = "country_weight")
    public int countryWeight;
}
