package com.example.weather_statistics.utils;

import android.net.Uri;

import com.example.weather_statistics.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    public static String PARAM_API_KEY = "apikey";

    public static URL generateRequestAccuweather (String locationID){
        Uri buildRequest = Uri.parse(Constants.ACCUWEATHER_COM + Constants.ACCUWEATHER_FORECAST_5day
                + locationID)
                        .buildUpon()
                        .appendQueryParameter(PARAM_API_KEY, Constants.ACCUWEATHER_API_KEY)
                        .build();

        URL requestAccuweather = null;
        try {
            requestAccuweather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return requestAccuweather;
    }
}




