package com.example.weather.common.configurations;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Application application;

    ContextModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    App provideApp() {
        return (App) application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application;
    }
}
