package com.example.weather.weather_by_week

import androidx.lifecycle.ViewModel
import com.example.weather.common.apis.OpenweathermapApi
import com.example.weather.common.navigation.NavControllerFactory
import com.example.weather.common.viewModel.ViewModelKey
import com.example.weather.weather_by_week.data.local.AppDatabase
import com.example.weather.weather_by_week.router.WeatherByWeekRouter
import com.example.weather.weather_by_week.router.WeatherByWeekRouterImpl
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class WeatherByWeekModule {
    @Provides
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun provideWeatherViewModel(api: OpenweathermapApi, database: AppDatabase, router: WeatherByWeekRouter): ViewModel =
        WeatherViewModel(api, database, router)

    @Provides
    fun getWeatherByWeekRouter(navControllerFactory: NavControllerFactory): WeatherByWeekRouter =
        WeatherByWeekRouterImpl(navControllerFactory)
}