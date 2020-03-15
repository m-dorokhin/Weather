package com.example.weather.apis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class SixteenDaysWeather {

    public static class City {
        public int id;
        public String name;
        public Coord coord;
        public String country;
        public int population;
        public int timezone;
    }

    public static class Coord {
        public double lon;
        public double lat;
    }

    public static class ListItem {
        public long dt;
        public int sunrise;
        public int sunset;
        public Temp temp;
        public double pressure;
        public double humidity;
        public List<Weather> weather;
        public double speed;
        public int deg;
        public int clouds;
        public double rain;
        public double snow;
        public FeelsLike feels_like;
    }

    public static class FeelsLike {
        public double day;
        public double night;
        public double eve;
        public double morn;
    }

    public static class Temp {
        public double day;
        public double min;
        public double max;
        public double night;
        public double eve;
        public double morn;
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public City city;
    public int cod;
    public double message;
    public int cnt;
    public List<ListItem> list;
}
