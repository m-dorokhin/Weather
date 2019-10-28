package com.example.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weather.configurations.App;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.models.DayWeather;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        WeatherViewModelFactory factory = App.getComponent().getWeatherViewModelFactory();
        WeatherViewModel weather = ViewModelProviders.of(this, factory).get(WeatherViewModel.class);
        weather.setContext(this);
        binding.setWeather(weather);

        RecyclerView weatherRecycler = findViewById(R.id.weather_recycler);

        final WeatherByDayAdapter adapter = new WeatherByDayAdapter(new ArrayList<DayWeather>());
        weatherRecycler.setAdapter(adapter);
        weather.getSixteenDayWeathers().observe(this, new Observer<List<DayWeather>>() {
            @Override
            public void onChanged(List<DayWeather> dayWeathers) {
                adapter.setItems(dayWeathers);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherRecycler.setLayoutManager(layoutManager);
    }
}
