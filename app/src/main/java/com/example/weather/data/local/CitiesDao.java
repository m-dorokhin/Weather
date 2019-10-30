package com.example.weather.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class CitiesDao {
    @Insert
    public abstract void insertCities(List<City> cities);

    @Query("SELECT * FROM cities WHERE name LIKE :nameLike ORDER BY weight DESC, country_weight DESC, instr(lower(name), lower(:name)) ASC LIMIT 25;")
    public abstract List<City> getCities(String nameLike, String name);

    @Query("UPDATE cities SET weight = weight + 1 WHERE id = :id;")
    public abstract void updateWeight(long id);

    @Query("UPDATE cities SET country_weight = country_weight + 1 WHERE country = :country;")
    public abstract void updateCountryWeight(String country);

    @Transaction
    void updateCities(City city) {
        updateWeight(city.id);
        updateCountryWeight(city.country);
    }
}
