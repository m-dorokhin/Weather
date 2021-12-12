package com.example.weather.common.configurations;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class DataBindingAdapters {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
