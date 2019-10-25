package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.apis.OpenweathermapApi;
import com.example.weather.apis.models.TodayWeather;
import com.example.weather.configurations.App;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class WeatherViewModel extends ViewModel {

    private OpenweathermapApi api;
    private String apiKey = "21a8d636ae57d56ec6fb2ebb46d3e0b4";

    public final ObservableField<String> city = new ObservableField<>("Select city");
    public final ObservableDouble temp = new ObservableDouble();
    public final ObservableField<String> weather = new ObservableField<>("Weather");
    public final ObservableDouble pressure = new ObservableDouble();
    public final ObservableDouble humidity = new ObservableDouble();
    public final ObservableField<String> windDirection = new ObservableField<>("None");
    public final ObservableDouble windSpeed = new ObservableDouble();

    public class SendWebRequest extends AsyncTask<Call<TodayWeather>, Void, TodayWeather> {
        @Override
        public TodayWeather doInBackground(Call<TodayWeather>... request) {
            TodayWeather todayWeather = null;
            try {
                todayWeather = request[0].execute().body();
            } catch (IOException e) {
                Log.e("WeatherViewModel", "Call api trouble", e);
            }
            return todayWeather;
        }

        @Override
        public void onPostExecute(TodayWeather todayWeather){
            if (todayWeather != null) {
                city.set(todayWeather.name);

                if (todayWeather.weather != null && !todayWeather.weather.isEmpty())
                    weather.set(todayWeather.weather.get(0).main);

                if (todayWeather.main != null) {
                    temp.set(todayWeather.main.temp-273);
                    pressure.set(todayWeather.main.pressure);
                    humidity.set(todayWeather.main.humidity);
                }

                if (todayWeather.wind != null) {
                    windDirection.set(Integer.toString(todayWeather.wind.deg));
                    windSpeed.set(todayWeather.wind.speed);
                }
            }
        }
    }

    public WeatherViewModel(OpenweathermapApi api)
    {
        this.api = api;
        Call<TodayWeather> result = api.getTodayWeather(484646, apiKey);
        new SendWebRequest().execute(result);
    }
}
