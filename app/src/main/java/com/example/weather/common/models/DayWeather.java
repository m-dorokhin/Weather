package com.example.weather.common.models;

import com.example.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayWeather {
    public static interface Action<T1, T2> {
        public void execute(T1 v1, T2 v2);
    }

    private Action<Integer, Date> gotoDetailedDayWeather;
    private int cityId = 0;

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

    public void setGotoDetailedDayWeather(Action<Integer, Date> gotoDetailedDayWeather) {
        this.gotoDetailedDayWeather = gotoDetailedDayWeather;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void GotoDetailedDayWeather() {
        if (gotoDetailedDayWeather != null && this.cityId != 0 && this.date != null)
            gotoDetailedDayWeather.execute(this.cityId, this.date);
    }
}
