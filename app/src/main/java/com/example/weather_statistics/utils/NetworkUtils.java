package com.example.weather_statistics.utils;

import android.net.Uri;

import com.example.weather_statistics.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static URL generateURLAccuWeather(String locationID){
        Uri buildRequest = Uri.parse(Constants.ACCUWEATHER_COM + Constants.ACCUWEATHER_FORECAST_5day
                + locationID)
                        .buildUpon()
                        .appendQueryParameter("apikey", Constants.ACCUWEATHER_API_KEY)
                        .appendQueryParameter("metric", "true")
                        .build();

        URL requestAccuweather = null;
        try {
            requestAccuweather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return requestAccuweather;
    }

    public static URL generateURLOpenWeather(String locationID){
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

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
              httpURLConnection.disconnect();
        }
    }

}




