package com.example.weather.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.databinding.WeatherByDayBinding;
import com.example.weather.common.models.DayWeather;

import java.util.List;

public class WeatherByDayAdapter extends RecyclerView.Adapter<WeatherByDayAdapter.ViewHolder> {

    private List<DayWeather> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WeatherByDayBinding binding;

        public ViewHolder(@NonNull WeatherByDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DayWeather dayWeather)
        {
            binding.setWeather(dayWeather);
            binding.executePendingBindings();
        }
    }

    public WeatherByDayAdapter(List<DayWeather> weathers)
    {
        this.items = weathers;
    }

    @NonNull
    @Override
    public WeatherByDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WeatherByDayBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.weather_by_day, parent, false);
        return new WeatherByDayAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DayWeather> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
