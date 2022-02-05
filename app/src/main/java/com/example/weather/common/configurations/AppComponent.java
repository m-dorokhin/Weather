package com.example.weather.common.configurations;

import android.app.Application;
import androidx.annotation.NonNull;
import com.example.weather.common.activity.ActivityComponent;
import com.example.weather.common.activity.ActivityComponentBuilderModule;
import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.weather_by_week.data.local.CitiesDao;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ContextModule.class, MainModule.class, ActivityComponentBuilderModule.class})
public interface AppComponent {
    OpenweathermapApi getOpenweathermapApi();

    CitiesDao getCitesDao();

    ActivityComponent.Builder getActivityComponentBuilder();

    static AppComponent build(@NonNull Application application) {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(application))
                .build();
    }
}
