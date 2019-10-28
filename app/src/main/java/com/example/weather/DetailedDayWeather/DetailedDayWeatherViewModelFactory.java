package com.example.weather.DetailedDayWeather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.apis.OpenweathermapApi;

import java.util.Date;

public class DetailedDayWeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private OpenweathermapApi api;
    private int cityId;
    private Date date;

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DetailedDayWeatherViewModelFactory(OpenweathermapApi api) {
        this.api = api;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailedDayWeatherViewModel.class) {
            return (T) new DetailedDayWeatherViewModel(api, cityId, date);
        }
        return null;
    }
}
