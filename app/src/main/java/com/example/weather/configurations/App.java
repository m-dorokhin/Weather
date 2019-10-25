package com.example.weather.configurations;

import android.app.Application;

public class App extends Application {
    private static AppComponent component;

    public App() {
        super();
        component = DaggerAppComponent.create();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
