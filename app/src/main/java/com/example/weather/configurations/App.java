package com.example.weather.configurations;

import android.app.Application;

public class App extends Application {
    private static AppComponent component;

    public App() {
        super();
        component = AppComponent.build(this);
    }

    public static AppComponent getComponent() {
        return component;
    }
}
