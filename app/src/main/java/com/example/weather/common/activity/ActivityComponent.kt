package com.example.weather.common.activity

import androidx.lifecycle.ViewModelProvider
import com.example.weather.common.viewModel.ViewModelModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelModule::class])
interface ActivityComponent {
    fun getViewModelsFactory(): ViewModelProvider.Factory

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(module: ActivityModule): Builder
        fun build(): ActivityComponent
    }
}