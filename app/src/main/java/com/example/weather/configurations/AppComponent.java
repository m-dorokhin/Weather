package com.example.weather.configurations;

import com.example.weather.WeatherViewModel;
import com.example.weather.WeatherViewModelFactory;
import com.example.weather.apis.OpenweathermapApi;

import dagger.Component;

@Component(modules = {MainModule.class})
public interface AppComponent {
    OpenweathermapApi getOpenweathermapApi();

    WeatherViewModelFactory getWeatherViewModelFactory();

    void injectsWeatherViewModel(WeatherViewModel weatherViewModel);
}
