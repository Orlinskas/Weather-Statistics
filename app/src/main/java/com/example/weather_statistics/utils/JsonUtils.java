package com.example.weather_statistics.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static String getTemperatureAccuWeather(String response){
        byte temperatureOut = 0;
        int temperatureMin = 0;
        int temperatureMax = 0;
        String temperatureMinString = null;
        String temperatureMaxString = null;

        try {
            JSONObject jsonObjectAccuWeather = new JSONObject(response);
            JSONArray jsonArrayDailyForecast = jsonObjectAccuWeather.getJSONArray("DailyForecasts");
            JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(1);
            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("Temperature");
            JSONObject jsonObjectDayTemperatureMin = jsonObjectDayTemperature.getJSONObject("Minimum");
            temperatureMinString = jsonObjectDayTemperatureMin.getString("Value");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temperatureMinString;
    }

    public static String getTemperatureOpenWeather(String response){
        byte temperatureOut = 0;
        int temperatureMin = 0;
        int temperatureMax = 0;
        String temperatureMinString = null;
        String temperatureMaxString = null;

        try {
            JSONObject jsonObjectOpenWeather = new JSONObject(response);
            JSONArray jsonArrayDays = jsonObjectOpenWeather.getJSONArray("list");
            JSONObject jsonObjectDay = jsonArrayDays.getJSONObject(1);
            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("main");
            temperatureMinString = jsonObjectDayTemperature.getString("temp");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temperatureMinString;
    }
}
