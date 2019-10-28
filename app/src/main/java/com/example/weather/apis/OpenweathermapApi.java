package com.example.weather.apis;

import com.example.weather.apis.models.HourlyWeather;
import com.example.weather.apis.models.SixteenDaysWeather;
import com.example.weather.apis.models.TodayWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenweathermapApi {
    @GET("data/2.5/weather")
    Call<TodayWeather> getTodayWeather(@Query("id") int cityId, @Query("appid") String appId);

    @GET("data/2.5/forecast/daily")
    Call<SixteenDaysWeather> getSixteenDayWeather(@Query("id") int cityId, @Query("appid") String appId);

    @GET("data/2.5/forecast")
    Call<HourlyWeather> getHourlyWeather(@Query("id") int cityId, @Query("appid") String appId);
}
