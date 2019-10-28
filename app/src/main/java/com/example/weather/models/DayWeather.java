package com.example.weather.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayWeather {
    public Date date;
    public double temp = 0;
    public String weather = "Weather";
    public double pressure = 0;
    public double humidity = 0;
    public String windDirection = "None";
    public double windSpeed = 0;

    public String getFormatedDate() {
        return new SimpleDateFormat("E yyyy.MM.dd").format(this.date);
    }
}
