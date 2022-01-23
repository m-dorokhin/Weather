package com.example.weather.weather_by_day;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.common.apis.OpenweathermapApi;

import java.util.Date;

public class DetailedDayWeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private OpenweathermapApi api;

    public DetailedDayWeatherViewModelFactory(OpenweathermapApi api) {
        this.api = api;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailedDayWeatherViewModel.class) {
            return (T) new DetailedDayWeatherViewModel(api);
        }
        return null;
    }
}
