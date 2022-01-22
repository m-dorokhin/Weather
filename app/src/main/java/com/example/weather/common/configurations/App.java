package com.example.weather.common.configurations;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {
    private static AppComponent component;

    public App() {
        super();
        component = AppComponent.build(this);
    }

    public static AppComponent getComponent() {
        return component;
    }
}
