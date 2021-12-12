package com.example.weather.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.weather.*;
import com.example.weather.common.WeatherByDayAdapter;
import com.example.weather.configurations.App;
import com.example.weather.data.local.City;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.models.DayWeather;

import java.util.ArrayList;
import java.util.List;

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

        AutoCompleteTextView cityTitle = (AutoCompleteTextView) findViewById(R.id.city);
        cityTitle.setThreshold(4);
        cityTitle.setAdapter(new CitiesAdapter(this, App.getComponent().getCitesDao()));
        cityTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                City city = (City) adapterView.getItemAtPosition(position);
                cityTitle.setText(city.name);
                weather.setCityId(city.id);
                weather.updateCityWeight(city);

                // Скроем экранную клавиатуру
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(adapterView.getApplicationWindowToken(), 0);

                // Очищаем фокус
                cityTitle.clearFocus();
            }
        });
    }
}
