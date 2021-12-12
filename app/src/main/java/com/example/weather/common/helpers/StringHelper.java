package com.example.weather.common.helpers;

import com.example.weather.R;

public class StringHelper {

    /**
     * Поулучить указатель на ресурс с назвнием направления ветра
     * @param windDirection Азимкт направления ветра
     * @return Номер ресурса с названием направления ветра
     * @throws Exception Достигнут недостижимый код
     */
    public static int GetWindDirectionName(int windDirection) throws Exception {
        int degres = windDirection % 360;

        if ((degres >= 0 && degres < 11.25) || (degres > 348.75 && degres <=360))
            return R.string.n;

        if (degres > 11.25 && degres < 33.75)
            return R.string.nne;

        if (degres > 33.75 && degres < 56.25)
            return R.string.ne;

        if (degres > 56.25 && degres < 78.75)
            return R.string.ene;

        if (degres > 78.75 && degres < 101.25)
            return R.string.e;

        if (degres > 101.25 && degres < 123.75)
            return  R.string.ese;

        if (degres > 123.75 && degres < 146.25)
            return R.string.se;

        if (degres > 146.25 && degres < 168.75)
            return R.string.sse;

        if (degres > 168.75 && degres < 191.25)
            return R.string.s;

        if (degres > 191.25 && degres < 213.75)
            return R.string.ssw;

        if (degres > 213.75 && degres < 236.25)
            return R.string.sw;

        if (degres > 236.25 && degres < 258.75)
            return R.string.wsw;

        if (degres > 258.75 && degres < 281.25)
            return R.string.w;

        if (degres > 281.25 && degres < 303.75)
            return R.string.wnw;

        if (degres > 303.75 && degres < 326.25)
            return R.string.nw;

        if (degres > 326.25 && degres < 348.75)
            return R.string.nnw;

        throw new Exception("Unreachable code reached");
    }
}
