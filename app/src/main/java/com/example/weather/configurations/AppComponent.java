package com.example.weather.configurations;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.weather.DetailedDayWeather.DetailedDayWeatherViewModelFactory;
import com.example.weather.main.WeatherViewModel;
import com.example.weather.main.WeatherViewModelFactory;
import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.main.data.local.CitiesDao;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, MainModule.class})
public interface AppComponent {
    OpenweathermapApi getOpenweathermapApi();

    WeatherViewModelFactory getWeatherViewModelFactory();

    DetailedDayWeatherViewModelFactory getDetailedDayWeatherViewModelFactory();

    CitiesDao getCitesDao();

    void injectsWeatherViewModel(WeatherViewModel weatherViewModel);


    static AppComponent build(@NonNull Application application) {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(application))
                .build();
    }
}
