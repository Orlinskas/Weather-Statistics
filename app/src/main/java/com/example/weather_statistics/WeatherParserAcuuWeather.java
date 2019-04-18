package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherParserAcuuWeather implements WeatherSourceResponceParceInterface {
    public Weather parse(String json){
        Weather weather = new Weather();
        String date = null;
        String temperatureMin = null;
        String temperatureMax = null;
        String sourse = null;
        String link = null;

        try {
            JSONObject jsonObjectAccuWeather = new JSONObject(json);

            JSONArray jsonArrayDailyForecast = jsonObjectAccuWeather.getJSONArray("DailyForecasts");
            JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(1);
            date = jsonObjectDay.getString("Date");

            JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("Temperature");
            JSONObject jsonObjectDayTemperatureMin = jsonObjectDayTemperature.getJSONObject("Minimum");
            JSONObject jsonObjectDayTemperatureMax = jsonObjectDayTemperature.getJSONObject("Maximum");
            temperatureMin = jsonObjectDayTemperatureMin.getString("Value");
            temperatureMax = jsonObjectDayTemperatureMax.getString("Value");

            JSONObject jsonObjectDaySource = jsonObjectDay.getJSONObject("Sources");
            sourse = jsonObjectDaySource.getString("0");

            link = jsonObjectDay.getString("Link");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ZZZ" , Locale.ENGLISH);
            weather.setDate(format.parse(date));
            weather.setTemperature(temperatureParser(temperatureMin, temperatureMax));
            weather.setSource(sourse);
            weather.setLocation(linkParserToLocation(link));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weather;
    }

    private String linkParserToLocation(String link){
        ArrayList<Character> town = new ArrayList<>();
        int count = 0;

        for (char ch : link.toCharArray()){
            if (ch == '/'){
                count++;
            }
            if (count==5){
                town.add(ch);
            }
            if (count==6){
                break;
            }
        }

        StringBuilder builder = new StringBuilder(town.size());

        for (Character ch : town){
            builder.append(ch);
        }

        return builder.toString();
    }

    private int temperatureParser(String tempMin, String tempMax){
        int tMin = Integer.parseInt(tempMin);
        int tMax = Integer.parseInt(tempMax);
        return (tMin + tMax) / 2;
    }
}
