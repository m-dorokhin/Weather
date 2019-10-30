package com.example.weather.DetailedDayWeather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.WeatherByDayAdapter;
import com.example.weather.configurations.App;
import com.example.weather.models.DayWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailedDayWeatherActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_ID = "city_id";
    public static final String EXTRA_DATE = "date";

    @SuppressLint({"LongLogTag", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_day_weather);

        Intent intent = getIntent();
        int cityId = intent.getIntExtra(EXTRA_CITY_ID, 484646);
        long dateTimestamp = intent.getLongExtra(EXTRA_DATE, new Date().getTime());
        Date date = new Date(dateTimestamp);

        Log.i("DetailedDayWeatherActivity",
                "Load daily weather for cityId: " + cityId + " and date: " +
                        new SimpleDateFormat("E yyyy.MM.dd").format(date));

        DetailedDayWeatherViewModelFactory factory = App.getComponent()
                .getDetailedDayWeatherViewModelFactory();
        factory.setCityId(cityId);
        factory.setDate(date);
        DetailedDayWeatherViewModel weather = ViewModelProviders.of(this, factory)
                .get(DetailedDayWeatherViewModel.class);

        RecyclerView weatherRecycler = findViewById(R.id.on_day_recycler);

        final WeatherByDayAdapter adapter = new WeatherByDayAdapter(new ArrayList<DayWeather>());
        weatherRecycler.setAdapter(adapter);
        weather.getDetailedWeathers().observe(this, new Observer<List<DayWeather>>() {
            @Override
            public void onChanged(List<DayWeather> dayWeathers) {
                adapter.setItems(dayWeathers);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherRecycler.setLayoutManager(layoutManager);
    }
}
