package com.example.weather.DetailedDayWeather;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.R;
import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.common.apis.models.HourlyWeather;
import com.example.weather.common.helpers.IconHelper;
import com.example.weather.common.helpers.StringHelper;
import com.example.weather.common.models.DayWeather;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedDayWeatherViewModel extends ViewModel {

    private OpenweathermapApi api;
    private String apiKey = "21a8d636ae57d56ec6fb2ebb46d3e0b4";
    private int cityId;
    private Date date;

    private final MutableLiveData<List<DayWeather>> detailedWeathers = new MutableLiveData<>();

    public DetailedDayWeatherViewModel(OpenweathermapApi api, int cityId, @NonNull Date date){
        this.api = api;
        this.cityId = cityId;
        this.date = date;

        GetWeather();
    }

    public LiveData<List<DayWeather>> getDetailedWeathers() {
        return detailedWeathers;
    }

    /**
     * Получить погоду для указанного города
     */
    private void GetWeather() {
        api.getHourlyWeather(cityId, this.apiKey).enqueue(new Callback<HourlyWeather>() {
            @Override
            public void onResponse(Call<HourlyWeather> call, Response<HourlyWeather> response) {

                // Получаем даты начала и окончания дня
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date startDate = calendar.getTime();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date endDate = calendar.getTime();

                HourlyWeather hourlyWeathers = response.body();

                List<DayWeather> result = new LinkedList<>();
                if (hourlyWeathers != null && hourlyWeathers.list != null && !hourlyWeathers.list.isEmpty()) {
                    for (HourlyWeather.ListItem item : hourlyWeathers.list) {
                        DayWeather dayWeather = new DayWeather();

                        dayWeather.date= new Date(item.dt * 1000);

                        // Если дата не попадает в выбранный день пропускаем блок данных
                        if (dayWeather.date.before(startDate) || dayWeather.date.after(endDate))
                            continue;

                        if (item.main != null) {
                            dayWeather.temp = item.main.temp - 273;
                            dayWeather.pressure = item.main.pressure;
                            dayWeather.humidity = item.main.humidity;
                        }

                        if (item.weather != null && !item.weather.isEmpty()) {
                            HourlyWeather.Weather weather = item.weather.get(0);
                            dayWeather.weather = weather.main;
                            dayWeather.weatherIcon = IconHelper.GetIcon(weather.icon);
                        }

                        if (item.wind != null) {
                            try {
                                dayWeather.windDirection = StringHelper.GetWindDirectionName(item.wind.deg);
                            } catch (Exception e) {
                                Log.wtf("WeatherViewModel", e);
                                dayWeather.windDirection = R.string.wind_direction;
                            }
                            dayWeather.windSpeed = item.wind.speed;
                        }

                        result.add(dayWeather);
                    }
                }
                detailedWeathers.postValue(result);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<HourlyWeather> call, Throwable t) {
                Log.w("DetailedDayWeatherViewModel", "Call api trouble", t);
            }
        });
    }
}
