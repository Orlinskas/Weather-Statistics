package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WeatherParserOpenWeather implements WeatherSourceResponseParseInterface {

    @Override
    public ArrayList<Weather> parse(String json){
        ArrayList<Weather> weathers = new ArrayList<>();

        String effectiveDate = null;
        String date = null;
        String temperature = null;
        String sourse = "OpenWeatherMap";
        String location = null;

        for (int index = 0; index <= 39; index++){
            try {
                JSONObject jsonObjectOpenWeather = new JSONObject(json);
                JSONArray jsonArrayDailyForecast = jsonObjectOpenWeather.getJSONArray("list");
                JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(index);

                if(index == 0){
                    effectiveDate = jsonObjectDay.getString("dt_txt");
                }

                date = jsonObjectDay.getString("dt_txt");

                JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("main");
                temperature = jsonObjectDayTemperature.getString("temp");

                JSONObject jsonObjectCity = jsonObjectOpenWeather.getJSONObject("city");
                location = jsonObjectCity.getString("name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Weather weather = new Weather();
            weather.setDate(date);
            weather.setEffectiveDate(effectiveDate);
            weather.setTemperature(Float.parseFloat(temperature));
            weather.setSource(sourse);
            weather.setLocation(location);
            weathers.add(weather);
        }

        return weathers;
    }
}
