package com.example.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.apis.OpenweathermapApi;

public class WeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private OpenweathermapApi api;

    public WeatherViewModelFactory(OpenweathermapApi api) {
        this.api = api;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == WeatherViewModel.class) {
            return (T) new WeatherViewModel(api);
        }
        return null;
    }
}
