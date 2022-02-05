package com.example.weather.common.activity

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {
    @ActivityScope
    @Provides
    fun provideActivity(): Activity = activity
}