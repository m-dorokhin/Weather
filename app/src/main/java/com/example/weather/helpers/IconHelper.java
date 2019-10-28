package com.example.weather.helpers;

import com.example.weather.R;

public class IconHelper {

    /**
     * Получить указатель на ресурс иконки
     * @param iconId Идентификатор иконки приходящий в api
     * @return Указатель на ресурс иконки
     */
    public static int GetIcon(String iconId){
        switch (iconId){
            case "01d":
                return R.drawable.d_01_clear_sky;

            case "02d":
                return R.drawable.d_02_few_clouds;

            case "03d":
                return R.drawable.d_03_scattered_clouds;

            case "04d":
                return R.drawable.d_04_broken_clouds;

            case "09d":
                return R.drawable.d_09_shower_rain;

            case "10d":
                return R.drawable.d_10_rain;

            case "11d":
                return R.drawable.d_11_thunderstorm;

            case "13d":
                return R.drawable.d_13_snow;

            case "50d":
                return R.drawable.d_50_mist;

            case "01n":
                return R.drawable.n_01_clear_sky;

            case "02n":
                return R.drawable.n_02_few_clouds;

            case "03n":
                return R.drawable.n_03_scattered_clouds;

            case "04n":
                return R.drawable.n_04_broken_clouds;

            case "09n":
                return R.drawable.n_09_shower_rain;

            case "10n":
                return R.drawable.n_10_rain;

            case "11n":
                return R.drawable.n_11_thunderstorm;

            case "13n":
                return R.drawable.n_13_snow;

            case "50n":
                return R.drawable.n_50_mist;

            default:
                throw new IllegalArgumentException("Неудаль обнаружить иконку с ид: " + iconId);
        }
    }
}
