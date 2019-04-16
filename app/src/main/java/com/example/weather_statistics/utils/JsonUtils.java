package com.example.weather_statistics.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static String getTemperatureAccuweather (String responce){
        byte temperatureOut = 0;
        int temperatureMin = 0;
        int temperatureMax = 0;
        String temperatureMinString = null;
        String temperatureMaxString = null;

        try {
            JSONObject jsonObjectAccuweather = new JSONObject(responce);
            JSONArray jsonArrayDailyForecast = jsonObjectAccuweather.getJSONArray("DailyForecasts");
            JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(1);
            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("Temperature");
            JSONObject jsonObjectDayTemperatureMin = jsonObjectDayTemperature.getJSONObject("Minimum");
            temperatureMinString = jsonObjectDayTemperatureMin.getString("Value");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temperatureMinString;
    }

    public static String getTemperatureOpenweathermap (String responce){
        byte temperatureOut = 0;
        int temperatureMin = 0;
        int temperatureMax = 0;
        String temperatureMinString = null;
        String temperatureMaxString = null;

        try {
            JSONObject jsonObjectOpenweathermap = new JSONObject(responce);
            JSONArray jsonArrayDays = jsonObjectOpenweathermap.getJSONArray("list");
            JSONObject jsonObjectDay = jsonArrayDays.getJSONObject(1);
            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("main");
            temperatureMinString = jsonObjectDayTemperature.getString("temp");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temperatureMinString;
    }
}
