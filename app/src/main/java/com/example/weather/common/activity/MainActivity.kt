package com.example.weather.common.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R
import com.example.weather.common.activity.ActivityComponent
import com.example.weather.common.activity.ActivityComponentHolder
import com.example.weather.common.activity.ActivityModule
import com.example.weather.common.configurations.App

class MainActivity : AppCompatActivity(), ActivityComponentHolder {
    private val component: ActivityComponent = App.getComponent()
        .activityComponentBuilder
        .activityModule(ActivityModule(this))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getActivityComponent(): ActivityComponent = component
}