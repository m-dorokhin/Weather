package com.example.weather.common.configurations;

import android.content.Context;
import androidx.room.Room;
import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.weather_by_week.data.local.AppDatabase;
import com.example.weather.weather_by_week.data.local.CitiesDao;
import com.example.weather.weather_by_week.data.local.FillDataIntoDb;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Singleton;

@Module
public class MainModule {
    @Provides
    public OpenweathermapApi getOpenweathermapApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(OpenweathermapApi.class);
    }

    @Provides
    public FillDataIntoDb getFillDataIntoDb(App application, Context context) {
        return new FillDataIntoDb(application, context);
    }

    @Provides
    @Singleton
    public AppDatabase getAppDatabase(Context context, FillDataIntoDb fillDataIntoDb) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "database")
                .addCallback(fillDataIntoDb)
                .allowMainThreadQueries() // TODO: Добавить работу с базой в отдельном потоке
                .build();
    }

    @Provides
    @Singleton
    public CitiesDao getCitiesDao(AppDatabase database) {
        return database.citiesDao();
    }
}
