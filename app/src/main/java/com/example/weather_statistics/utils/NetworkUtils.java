package com.example.weather_statistics.utils;

import android.net.Uri;

import com.example.weather_statistics.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static URL generateRequestAccuweather (String locationID){
        Uri buildRequest = Uri.parse(Constants.ACCUWEATHER_COM + Constants.ACCUWEATHER_FORECAST_5day
                + locationID)
                        .buildUpon()
                        .appendQueryParameter("apikey", Constants.ACCUWEATHER_API_KEY)
                        .build();

        URL requestAccuweather = null;
        try {
            requestAccuweather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return requestAccuweather;
    }

    public static URL generateRequestOpenweathermap(String locationID){
        Uri buildRequest = Uri.parse(Constants.OPENWEATHERMAP_COM + Constants.OPENWEATHERMAP_FORECAST_5day)
                .buildUpon()
                .appendQueryParameter("id", locationID)
                .appendQueryParameter("APPID", Constants.OPENWEATHERMAP_API_KEY)
                .appendQueryParameter("units", "metric")
                .build();

        URL requestOpenweathermap = null;
        try {
            requestOpenweathermap = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return requestOpenweathermap;
    }
}




