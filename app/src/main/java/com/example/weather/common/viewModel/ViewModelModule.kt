package com.example.weather.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.common.apis.OpenweathermapApi
import com.example.weather.weather_by_day.DetailedDayWeatherViewModel
import com.example.weather.weather_by_week.WeatherViewModel
import com.example.weather.weather_by_week.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelModule {
    @Provides
    fun provideViewModelsFactory(
        viewModelProviders: MutableMap<Class<out ViewModel>, /*@JvmSuppressWildcards*/ Provider<ViewModel>>,
    ): ViewModelProvider.Factory = ViewModelsFactory(viewModelProviders)

    @Provides
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun provideWeatherViewModel(api: OpenweathermapApi, database: AppDatabase): ViewModel =
        WeatherViewModel(api, database)

    @Provides
    @IntoMap
    @ViewModelKey(DetailedDayWeatherViewModel::class)
    fun provideDetailedDayWeatherViewModel(api: OpenweathermapApi): ViewModel = DetailedDayWeatherViewModel(api)
}