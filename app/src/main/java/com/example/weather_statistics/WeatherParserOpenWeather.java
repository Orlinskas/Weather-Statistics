package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherParserOpenWeather implements WeatherSourceResponceParceInterface {
    public Weather parse(String json){
        Weather weather = new Weather();
        String date = null;
        String temperature = null;
        String sourse = "OpenWeatherMap";
        String location = null;

        try {
            JSONObject jsonObjectOpenWeather = new JSONObject(json);
            JSONArray jsonArrayDailyForecast = jsonObjectOpenWeather.getJSONArray("list");
            JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(0);
            date = jsonObjectDay.getString("dt_txt");

            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("main");
            temperature = jsonObjectDayTemperature.getString("temp");

            JSONObject jsonObjectCity = jsonObjectOpenWeather.getJSONObject("city");
            location = jsonObjectCity.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.ENGLISH);
            weather.setDate(format.parse(date));
            weather.setTemperature(Integer.parseInt(temperature));
            weather.setSource(sourse);
            weather.setLocation(location);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weather;
    }
}
