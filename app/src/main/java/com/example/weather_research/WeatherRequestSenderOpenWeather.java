package com.example.weather_research;

import android.net.Uri;

import com.example.weather_research.date.DatabaseAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherRequestSenderOpenWeather implements WeatherRequestSenderInterface {

    private DatabaseAdapter database = new DatabaseAdapter(AppContext.getContext());

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
            database.open();
            database.insertLastErrorText(Constants.ERROR_CONNECTION, WeatherRequestSenderAccuWeather.class);
            database.close();
        } finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    private static URL generateURL(String locationID){
        Uri buildRequest = Uri.parse(Constants.OPENWEATHERMAP_COM + Constants.OPENWEATHERMAP_FORECAST_5day)
                .buildUpon()
                .appendQueryParameter("id", locationID)
                .appendQueryParameter("APPID", Constants.OPENWEATHERMAP_API_KEY)
                .appendQueryParameter("units", "metric")
                .build();

        URL requestOpenWeather = null;
        try {
            requestOpenWeather = new URL(buildRequest.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            DatabaseAdapter database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            database.insertLastErrorText(Constants.ERROR_URL, WeatherRequestSenderOpenWeather.class);
            database.close();
        }

        return requestOpenWeather;
    }
}
