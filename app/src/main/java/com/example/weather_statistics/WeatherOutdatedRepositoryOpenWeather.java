package com.example.weather_statistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.weather_statistics.date.WeatherContract;
import com.example.weather_statistics.date.WeatherSQLiteHelper;
import com.example.weather_statistics.date.WeatherContract.WeatherEntry;

import java.util.Date;

public class WeatherOutdatedRepositoryOpenWeather implements WeatherOutdatedRepositoryInterface {

    @Override
    public boolean save(Weather weather) {
        Context context = null;
        Date effectiveDate = new Date();
        String date = null;
        String temperature = null;
        String sourse = "OpenWeatherMap";
        String location = null;



        //БД надо брать от куда то контекст, которого у меня нет
        WeatherSQLiteHelper weatherSQLiteHelper = new WeatherSQLiteHelper(context);
        SQLiteDatabase db = weatherSQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeatherEntry.COLUMN_EFFECTIVE_DATE, String.valueOf(effectiveDate));
        values.put(WeatherEntry.COLUMN_DATE, date);
        values.put(WeatherEntry.COLUMN_TEMPERATURE, temperature);
        values.put(WeatherEntry.COLUMN_LOCATION, location);
        values.put(WeatherEntry.COLUMN_SOURCE, sourse);

        long newRowId = db.insert(WeatherEntry.TABLE_NAME, null, values);

        return false;
    }
}
