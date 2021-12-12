package com.example.weather.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.main.data.local.AppDatabase;

public class WeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private OpenweathermapApi api;
    private AppDatabase database;

    public WeatherViewModelFactory(OpenweathermapApi api, AppDatabase database) {
        this.api = api;
        this.database = database;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == WeatherViewModel.class) {
            return (T) new WeatherViewModel(api, database);
        }
        return null;
    }
}
