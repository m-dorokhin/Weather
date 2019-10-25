package com.example.weather.configurations;

import com.example.weather.WeatherViewModelFactory;
import com.example.weather.apis.OpenweathermapApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class MainModule {
    @Provides
    public OpenweathermapApi getOpenweathermapApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(OpenweathermapApi.class);
    }

    @Provides
    public WeatherViewModelFactory getWeatherViewModelFactory(OpenweathermapApi api) {
        return new WeatherViewModelFactory(api);
    }
}
