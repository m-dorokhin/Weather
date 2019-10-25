package com.example.weather.apis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class TodayWeather {

    public class Coord {
        public double lon;
        public double lat;
    }

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Main {
        public double temp;
        public double pressure;
        public double humidity;
        public double temp_min;
        public double temp_max;
        public int sea_level;
        public int grnd_level;
    }

    public class Wind {
        public double speed;
        public int deg;
    }

    public class Clouds {
        public int all;
    }

    public class Sys {
        public int type;
        public int id;
        public double message;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public Coord coord;
    @JsonIgnore
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
