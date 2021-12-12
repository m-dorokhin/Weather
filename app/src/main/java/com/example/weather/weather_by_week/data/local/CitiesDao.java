package com.example.weather.weather_by_week.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class CitiesDao {
    @Insert
    public abstract void insertCities(List<City> cities);

    @Query("SELECT * FROM cities WHERE id = :id")
    public abstract City getCity(long id);

    @Query("SELECT * FROM cities WHERE name LIKE :nameLike ORDER BY weight DESC, country_weight DESC, instr(lower(name), lower(:name)) ASC;")
    public abstract List<City> searchCities(String nameLike, String name);

    @Query("UPDATE cities SET weight = weight + 1 WHERE id = :id;")
    public abstract void updateWeight(long id);

    @Query("UPDATE cities SET country_weight = country_weight + 1 WHERE country = :country;")
    public abstract void updateCountryWeight(String country);

    @Transaction
    public void updateCities(City city) {
        updateWeight(city.id);
        updateCountryWeight(city.country);
    }
}
