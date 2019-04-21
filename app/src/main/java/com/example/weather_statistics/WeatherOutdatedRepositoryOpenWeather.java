package com.example.weather_statistics;

import android.content.Context;

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
        //DatabaseHelper dataBaseHelper = new DatabaseHelper(context);
       // SQLiteDatabase db = dataBaseHelper.getWritableDatabase();



        return false;
    }
}
