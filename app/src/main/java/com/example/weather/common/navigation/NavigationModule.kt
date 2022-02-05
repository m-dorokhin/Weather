package com.example.weather.common.navigation

import android.app.Activity
import androidx.navigation.findNavController
import com.example.weather.R
import com.example.weather.common.activity.ActivityModule
import com.example.weather.common.activity.ActivityScope
import dagger.Module
import dagger.Provides

@Module(includes = [ActivityModule::class])
class NavigationModule {
    @ActivityScope
    @Provides
    fun provideNavControllerFactory(activity: Activity) =
        NavControllerFactory { activity.findNavController(R.id.fragment_container) }
}