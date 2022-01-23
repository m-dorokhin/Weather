package com.example.weather.common.activity

import android.app.Activity
import dagger.Module
import dagger.Provides

@ActivityScope
@Module
class ActivityModule(private val activity: Activity) {
    @Provides
    fun provideActivity(): Activity = activity
}