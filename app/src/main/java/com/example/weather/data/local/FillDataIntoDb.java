package com.example.weather.data.local;

import android.content.Context;
import android.util.Log;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.weather.configurations.App;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FillDataIntoDb extends RoomDatabase.Callback {
    private static final String DATA_FILE_NAME = "city.list.tsv";
    private static final int POS_ID = 0;
    private static final int POS_NAME = 1;
    private static final int POS_COUNTRY = 2;
    private static final int POS_LON = 3;
    private static final int POS_LAT = 4;

    private App application;
    private Context context;

    public FillDataIntoDb(App application, Context context) {
        this.application = application;
        this.context = context;
    }

    public void onCreate (SupportSQLiteDatabase db) {
        Log.d("FillDataIntoDb", "Fill data into db started");
        Executors.newSingleThreadExecutor().execute(() -> {
            CitiesDao dao = application.getComponent().getCitesDao();
            List<City> cities = getCities();
            dao.insertCities(cities);
            Log.d("FillDataIntoDb", "Fill data into db ended");
        });
    }

    private List<City> getCities() {
        InputStream stream = null;
        ArrayList<City> list = new ArrayList<>();
        try {
            stream = context.getAssets().open(DATA_FILE_NAME);
        } catch (IOException e) {
            Log.e("FillDataIntoDb", "Can not open data file", e);
        }

        DataInputStream dataStream = new DataInputStream(stream);
        String data = "";
        try {
            while( (data=dataStream.readLine()) != null ) {
                String[] line = data.split("\t");

                if (line == null) {
                    Log.w("FillDataIntoDb", "line is null");
                    continue;
                }
                if (line.length == 0) {
                    Log.w("FillDataIntoDb", "line is empty");
                    continue;
                }

                City item = new City();
                item.id = Integer.parseInt(line[POS_ID]);
                item.name = line[POS_NAME];
                item.country = line[POS_COUNTRY];
                item.lon = Double.parseDouble(line[POS_LON]);
                item.lat = Double.parseDouble(line[POS_LAT]);
                item.weight = 0;
                item.countryWeight = 0;
                list.add(item);
            }
        }
        catch (IOException e) {
            Log.e("FillDataIntoDb", "Can not read data file", e);
            //e.printStackTrace();
        }

        return list;
    }
}
