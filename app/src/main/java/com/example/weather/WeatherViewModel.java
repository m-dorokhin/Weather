package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.apis.OpenweathermapApi;
import com.example.weather.apis.models.SixteenDaysWeather;
import com.example.weather.apis.models.TodayWeather;
import com.example.weather.configurations.App;
import com.example.weather.models.DayWeather;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class WeatherViewModel extends ViewModel {

    private OpenweathermapApi api;
    private String apiKey = "21a8d636ae57d56ec6fb2ebb46d3e0b4";
    private int cityId = 484646;

    public final ObservableField<String> city = new ObservableField<>("Select city");
    public final ObservableDouble temp = new ObservableDouble();
    public final ObservableField<String> weather = new ObservableField<>("Weather");
    public final ObservableDouble pressure = new ObservableDouble();
    public final ObservableDouble humidity = new ObservableDouble();
    public final ObservableField<String> windDirection = new ObservableField<>("None");
    public final ObservableDouble windSpeed = new ObservableDouble();

    private final MutableLiveData<List<DayWeather>> sixteenDayWeathers = new MutableLiveData<>();

    public interface ResultDelegate<T> {
        public void execute(T value);
    }
    public class SendWebRequest<T> extends AsyncTask<Call<T>, Void, T> {

        private ResultDelegate<T> resultDelegate;

        public SendWebRequest(ResultDelegate<T> resultDelegate){
            this.resultDelegate = resultDelegate;
        }

        @Override
        public T doInBackground(Call<T>... request) {
            T todayWeather = null;
            try {
                todayWeather = request[0].execute().body();
            } catch (IOException e) {
                Log.e("WeatherViewModel", "Call api trouble", e);
            }
            return todayWeather;
        }

        @Override
        public void onPostExecute(T todayWeather){
            this.resultDelegate.execute(todayWeather);

        }
    }

    public WeatherViewModel(OpenweathermapApi api)
    {
        this.api = api;

        Call<TodayWeather> todayWeatherRequest = api.getTodayWeather(cityId, apiKey);
        new SendWebRequest<TodayWeather>(new ResultDelegate<TodayWeather>() {
            @Override
            public void execute(TodayWeather todayWeather) {
                if (todayWeather != null) {
                    city.set(todayWeather.name);

                    if (todayWeather.weather != null && !todayWeather.weather.isEmpty())
                        weather.set(todayWeather.weather.get(0).main);

                    if (todayWeather.main != null) {
                        temp.set(todayWeather.main.temp - 273);
                        pressure.set(todayWeather.main.pressure);
                        humidity.set(todayWeather.main.humidity);
                    }

                    if (todayWeather.wind != null) {
                        windDirection.set(Integer.toString(todayWeather.wind.deg));
                        windSpeed.set(todayWeather.wind.speed);
                    }
                }
            }
        }).execute(todayWeatherRequest);

        Call<SixteenDaysWeather> sixteenDaysRequest = api.getSixteenDayWeather(cityId, apiKey);
        new SendWebRequest<SixteenDaysWeather>(new ResultDelegate<SixteenDaysWeather>() {
            @Override
            public void execute(SixteenDaysWeather daysWeathers) {
                List<DayWeather> result = new LinkedList<>();
                if (daysWeathers != null && daysWeathers.list != null && !daysWeathers.list.isEmpty()) {
                    for (SixteenDaysWeather.ListItem item : daysWeathers.list) {
                        DayWeather dayWeather = new DayWeather();

                        dayWeather.date= new Date(item.dt * 1000);

                        if (item.temp != null)
                            dayWeather.temp = item.temp.day - 273;

                        if (item.weather != null && !item.weather.isEmpty())
                            dayWeather.weather = item.weather.get(0).main;

                        dayWeather.pressure = item.pressure;
                        dayWeather.humidity = item.humidity;

                        dayWeather.windDirection = Integer.toString(item.deg);
                        dayWeather.windSpeed = item.speed;

                        result.add(dayWeather);
                    }
                }
                sixteenDayWeathers.postValue(result);
            }
        }).execute(sixteenDaysRequest);
    }

    public LiveData<List<DayWeather>> getSixteenDayWeathers() {
        return sixteenDayWeathers;
    }
}
