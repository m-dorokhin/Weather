package com.example.weather.configurations;

import com.example.weather.DetailedDayWeather.DetailedDayWeatherViewModelFactory;
import com.example.weather.WeatherViewModel;
import com.example.weather.WeatherViewModelFactory;
import com.example.weather.apis.OpenweathermapApi;

import dagger.Component;

@Component(modules = {MainModule.class})
public interface AppComponent {
    OpenweathermapApi getOpenweathermapApi();

    WeatherViewModelFactory getWeatherViewModelFactory();

    DetailedDayWeatherViewModelFactory getDetailedDayWeatherViewModelFactory();

    void injectsWeatherViewModel(WeatherViewModel weatherViewModel);
}
