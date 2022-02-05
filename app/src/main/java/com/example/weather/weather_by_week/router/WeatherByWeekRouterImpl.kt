package com.example.weather.weather_by_week.router

import androidx.core.os.bundleOf
import com.example.weather.R
import com.example.weather.common.navigation.NavControllerFactory
import com.example.weather.weather_by_day.WeatherByDayFragment

class WeatherByWeekRouterImpl(private val getNavController: NavControllerFactory) : WeatherByWeekRouter {
    override fun gotoWeatherByDay(cityId: Int, date: Long) {
        getNavController().navigate(
            R.id.action_weatherByWeekFragment_to_weatherByDayFragment,
            bundleOf(
                WeatherByDayFragment.EXTRA_CITY_ID to cityId,
                WeatherByDayFragment.EXTRA_DATE to date
            )
        )
    }
}