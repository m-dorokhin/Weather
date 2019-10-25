package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weather.configurations.App;
import com.example.weather.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        WeatherViewModelFactory factory = App.getComponent().getWeatherViewModelFactory();
        WeatherViewModel weather = ViewModelProviders.of(this, factory).get(WeatherViewModel.class);
        binding.setWeather(weather);

        RecyclerView weatherRecycler = findViewById(R.id.weather_recycler);
        WeatherByDayAdapter adapter = new WeatherByDayAdapter();
        weatherRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherRecycler.setLayoutManager(layoutManager);
    }
}
