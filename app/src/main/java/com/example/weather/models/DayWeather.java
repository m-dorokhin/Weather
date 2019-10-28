package com.example.weather.models;

import com.example.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayWeather {
    public Date date;
    public double temp = 0;
    public String weather = "Weather";
    public double pressure = 0;
    public double humidity = 0;
    public int windDirection = R.string.wind_direction;
    public double windSpeed = 0;
    public int weatherIcon = R.drawable.d_01_clear_sky;

    public String getFormatedDate() {
        return new SimpleDateFormat("E yyyy.MM.dd H").format(this.date);
    }
}
