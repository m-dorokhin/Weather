package com.example.weather.apis.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class HourlyWeather {
    public static class City {
        public int id;
        public String name;
        public Coord coord;
        public String country;
        public int timezone;
        public long sunrise;
        public long sunset;
    }

    public static class Coord {
        public double lon;
        public double lat;
    }

    public static class ListItem {
        public long dt;
        public Main main;
        public List<Weather> weather;
        public Clouds clouds;
        public Wind wind;
        public Rain rain;
        public Snow snow;
        public Sys sys;
        public String dt_txt;
    }

    public static class Main {
        public double temp;
        public double temp_min;
        public double temp_max;
        public double pressure;
        public double sea_level;
        public double grnd_level;
        public int humidity;
        public double temp_kf;
        public double feels_like;
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Clouds {
        public int all;
    }

    public static class Wind {
        public int deg;
        public double speed;
    }

    public static class Rain {
        @JsonAlias("3h")
        public double three_h;
    }

    public static class Snow {
        @JsonAlias("3h")
        public double three_h;
    }

    public static class Sys {
        public String pod;
    }

    public City city;
    public String cod;
    public double message;
    public int cnt;
    public List<ListItem> list;
}