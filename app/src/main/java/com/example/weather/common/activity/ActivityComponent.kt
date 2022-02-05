package com.example.weather.common.activity

import androidx.lifecycle.ViewModelProvider
import com.example.weather.common.navigation.NavigationModule
import com.example.weather.common.viewModel.ViewModelModule
import com.example.weather.weather_by_week.WeatherByWeekModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [
    ActivityModule::class,
    NavigationModule::class,
    ViewModelModule::class,
    WeatherByWeekModule::class
])
interface ActivityComponent {
    fun getViewModelsFactory(): ViewModelProvider.Factory

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(module: ActivityModule): Builder
        fun build(): ActivityComponent
    }
}