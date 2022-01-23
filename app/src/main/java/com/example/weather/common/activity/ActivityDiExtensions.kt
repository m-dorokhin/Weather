package com.example.weather.common.activity

import android.app.Activity
import java.lang.IllegalArgumentException

fun Activity.getActivityComponent(): ActivityComponent {
    if (this !is ActivityComponentHolder)
        throw IllegalArgumentException("Activity должно реализовывать интерфейс ActivityComponentHolder")

    return this.getActivityComponent()
}