package com.example.weather;

import android.util.Log;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.apis.OpenweathermapApi;
import com.example.weather.apis.models.SixteenDaysWeather;
import com.example.weather.apis.models.TodayWeather;
import com.example.weather.helpers.IconHelper;
import com.example.weather.helpers.StringHelper;
import com.example.weather.models.DayWeather;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private OpenweathermapApi api;
    private String apiKey = "21a8d636ae57d56ec6fb2ebb46d3e0b4";
    private int cityId = 484646;

    public final ObservableField<String> city = new ObservableField<>("Select city");
    public final ObservableDouble temp = new ObservableDouble();
    public final ObservableField<String> weather = new ObservableField<>("Weather");
    public final ObservableDouble pressure = new ObservableDouble();
    public final ObservableDouble humidity = new ObservableDouble();
    public final ObservableInt windDirection = new ObservableInt(R.string.wind_direction);
    public final ObservableDouble windSpeed = new ObservableDouble();
    public final ObservableInt weatherIcon = new ObservableInt(R.drawable.d_01_clear_sky);

    private final MutableLiveData<List<DayWeather>> sixteenDayWeathers = new MutableLiveData<>();

    public WeatherViewModel(OpenweathermapApi api)
    {
        this.api = api;

        GetWeather(this.cityId);
    }

    public LiveData<List<DayWeather>> getSixteenDayWeathers() {
        return sixteenDayWeathers;
    }

    /**
     * Получить погоду для указанного города
     * @param cityId Идентификатор города
     */
    private void GetWeather(int cityId) {
        api.getTodayWeather(cityId, this.apiKey).enqueue(new Callback<TodayWeather>() {
            @Override
            public void onResponse(Call<TodayWeather> call, Response<TodayWeather> response) {
                TodayWeather todayWeather = response.body();

                if (todayWeather != null) {
                    city.set(todayWeather.name);

                    if (todayWeather.weather != null && !todayWeather.weather.isEmpty()) {
                        TodayWeather.Weather weatherStatus = todayWeather.weather.get(0);
                        weather.set(weatherStatus.main);
                        weatherIcon.set(IconHelper.GetIcon(weatherStatus.icon));
                    }

                    if (todayWeather.main != null) {
                        temp.set(todayWeather.main.temp - 273);
                        pressure.set(todayWeather.main.pressure);
                        humidity.set(todayWeather.main.humidity);
                    }

                    if (todayWeather.wind != null) {
                        try {
                            windDirection.set(StringHelper.GetWindDirectionName(todayWeather.wind.deg));
                        } catch (Exception e) {
                            Log.wtf("WeatherViewModel", e);
                            windDirection.set(R.string.wind_direction);
                        }
                        windSpeed.set(todayWeather.wind.speed);
                    }
                }
            }

            @Override
            public void onFailure(Call<TodayWeather> call, Throwable t) {
                Log.w("WeatherViewModel", "Call api trouble", t);
            }
        });

        api.getSixteenDayWeather(cityId, this.apiKey).enqueue(new Callback<SixteenDaysWeather>() {
            @Override
            public void onResponse(Call<SixteenDaysWeather> call, Response<SixteenDaysWeather> response) {
                SixteenDaysWeather daysWeathers = response.body();

                List<DayWeather> result = new LinkedList<>();
                if (daysWeathers != null && daysWeathers.list != null && !daysWeathers.list.isEmpty()) {
                    boolean first = true;
                    for (SixteenDaysWeather.ListItem item : daysWeathers.list) {
                        // Пропускаем первый элемент
                        if (first){
                            first = false;
                            continue;
                        }

                        DayWeather dayWeather = new DayWeather();

                        dayWeather.date= new Date(item.dt * 1000);

                        if (item.temp != null)
                            dayWeather.temp = item.temp.day - 273;

                        if (item.weather != null && !item.weather.isEmpty()) {
                            SixteenDaysWeather.Weather weather = item.weather.get(0);
                            dayWeather.weather = weather.main;
                            dayWeather.weatherIcon = IconHelper.GetIcon(weather.icon);
                        }

                        dayWeather.pressure = item.pressure;
                        dayWeather.humidity = item.humidity;

                        try {
                            dayWeather.windDirection = StringHelper.GetWindDirectionName(item.deg);
                        } catch (Exception e) {
                            Log.wtf("WeatherViewModel", e);
                            dayWeather.windDirection = R.string.wind_direction;
                        }
                        dayWeather.windSpeed = item.speed;

                        result.add(dayWeather);
                    }
                }
                sixteenDayWeathers.postValue(result);
            }

            @Override
            public void onFailure(Call<SixteenDaysWeather> call, Throwable t) {
                Log.w("WeatherViewModel", "Call api trouble", t);
            }
        });
    }
}
