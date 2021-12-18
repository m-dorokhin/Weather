package com.example.weather.weather_by_week;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.weather.R;
import com.example.weather.common.apis.OpenweathermapApi;
import com.example.weather.common.apis.models.SixteenDaysWeather;
import com.example.weather.common.apis.models.TodayWeather;
import com.example.weather.common.helpers.IconHelper;
import com.example.weather.common.helpers.StringHelper;
import com.example.weather.common.models.DayWeather;
import com.example.weather.weather_by_week.data.local.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WeatherViewModel extends ViewModel {

    private OpenweathermapApi api;
    private AppDatabase database;
    private String apiKey = "21a8d636ae57d56ec6fb2ebb46d3e0b4";

    private int cityId = 0;

    private Context context;

    private Date date = new Date();

    public final ObservableField<String> city = new ObservableField<>("");
    public final ObservableField<String> textDate = new ObservableField<>("");
    public final ObservableDouble temp = new ObservableDouble();
    public final ObservableField<String> weather = new ObservableField<>("Weather");
    public final ObservableDouble pressure = new ObservableDouble();
    public final ObservableDouble humidity = new ObservableDouble();
    public final ObservableInt windDirection = new ObservableInt(R.string.wind_direction);
    public final ObservableDouble windSpeed = new ObservableDouble();
    public final ObservableInt weatherIcon = new ObservableInt(R.drawable.d_01_clear_sky);

    private final MutableLiveData<List<DayWeather>> sixteenDayWeathers = new MutableLiveData<>();

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCityId(long id) {
        cityId = (int) id;
        GetWeather(cityId);
        SaveSettings();
    }

    public int getCityId() {
        return cityId;
    }

    public Date getDate() {
        return this.date;
    }

    public String printDate() {
        return new SimpleDateFormat("E yyyy.MM.dd").format(this.date);
    }

    public LiveData<List<DayWeather>> getSixteenDayWeathers() {
        return sixteenDayWeathers;
    }

    public WeatherViewModel(@NonNull OpenweathermapApi api, @NonNull AppDatabase database) {
        this.api = api;
        this.database = database;

        LoadSettings();
        GetWeather(this.cityId);
    }

    /**
     * Получить погоду для указанного города
     * @param cityId Идентификатор города
     */
    private void GetWeather(int cityId) {
        if (cityId == 0)
            return;

        api.getTodayWeather(cityId, this.apiKey).enqueue(new Callback<TodayWeather>() {
            @Override
            public void onResponse(Call<TodayWeather> call, Response<TodayWeather> response) {
                TodayWeather todayWeather = response.body();

                if (todayWeather != null) {
                    city.set(todayWeather.name);

                    date = new Date(todayWeather.dt * 1000);
                    textDate.set(printDate());

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


                        dayWeather.setCityId(getCityId());
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

    private void LoadSettings() {
        Log.i("WeatherViewModel", "Load settings");
        SettingsDao dao = this.database.settingsDao();
        Settings settings = dao.get();
        if (settings == null) {
            settings = new Settings();
            settings.id = 0;
            settings.selectCity = this.cityId;
            dao.insert(settings);
        }
        this.cityId = settings.selectCity;

        if (this.cityId != 0) {
            CitiesDao citiesDao = this.database.citiesDao();
            City city = citiesDao.getCity(this.cityId);
            this.city.set(city.name);
        }
    }
    
    private void SaveSettings() {
        Log.i("WeatherViewModel", "Save settings");
        SettingsDao dao = this.database.settingsDao();
        Settings settings = dao.get();
        if (settings == null) {
            settings = new Settings();
            settings.id = 0;
            settings.selectCity = this.cityId;
            dao.insert(settings);
        } else {
            settings.selectCity = this.cityId;
            dao.update(settings);
        }
    }

    public void updateCityWeight(City city) {
        CitiesDao dao = this.database.citiesDao();
        dao.updateCities(city);
    }
}
