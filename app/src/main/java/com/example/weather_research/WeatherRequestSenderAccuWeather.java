package com.example.weather_research;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherRequestSenderAccuWeather implements WeatherRequestSenderInterface {

    @Override
    public String requestWeather(String locationID) {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) generateURL(locationID).openConnection();
            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    private static URL generateURL(String locationID){
        Uri buildRequest = Uri.parse(Constants.ACCUWEATHER_COM + Constants.ACCUWEATHER_FORECAST_5day
                + locationID)
                .buildUpon()
                .appendQueryParameter("apikey", Constants.ACCUWEATHER_API_KEY)
                .appendQueryParameter("metric", "true")
                .build();

        URL requestAccuWeather = null;
        try {
            requestAccuWeather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return requestAccuWeather;
    }
}
