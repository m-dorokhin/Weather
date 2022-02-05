package com.example.weather.weather_by_week.router

interface WeatherByWeekRouter {
    fun gotoWeatherByDay(cityId: Int, date: Long)
}