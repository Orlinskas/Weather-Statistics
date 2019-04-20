package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherParserAcuuWeather implements WeatherSourceResponseParseInterface {

    @Override
    public ArrayList<Weather> parse(String json){
        ArrayList<Weather> weathers = new ArrayList<>();

        String date = null;
        String temperatureMin = null;
        String temperatureMax = null;
        String sourse = null;
        String link = null;

        for(int index = 1; index <= 4; index++ ) {
            try {
                JSONObject jsonObjectAccuWeather = new JSONObject(json);

                JSONArray jsonArrayDailyForecast = jsonObjectAccuWeather.getJSONArray("DailyForecasts");
                JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(index);
                date = jsonObjectDay.getString("Date");

                JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("Temperature");
                JSONObject jsonObjectDayTemperatureMin = jsonObjectDayTemperature.getJSONObject("Minimum");
                JSONObject jsonObjectDayTemperatureMax = jsonObjectDayTemperature.getJSONObject("Maximum");
                temperatureMin = jsonObjectDayTemperatureMin.getString("Value");
                temperatureMax = jsonObjectDayTemperatureMax.getString("Value");


                JSONArray jsonArrayDaySources = jsonObjectDay.getJSONArray("Sources");
                sourse = jsonArrayDaySources.getString(0);

                link = jsonObjectDay.getString("Link");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Weather weather = new Weather();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

            try {
                weather.setDate(format.parse(date));
                weather.setTemperature(temperatureParser(temperatureMin, temperatureMax));
                weather.setSource(sourse);
                weather.setLocation(linkParserToLocation(link));
                weathers.add(weather);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return weathers;
    }

    private String linkParserToLocation(String link){
        //поскольку название города содержится только в длинной строке джсон, мы его вырезаем
        ArrayList<Character> town = new ArrayList<>();
        int count = 0;

        for (char ch : link.toCharArray()){
            if (ch == '/'){
                count++;
            }
            if (count == 5){
                if (ch != '/') { //к сожалению алгоритм не совершеннен и приходится убирать первый слэш
                    town.add(ch);
                }
            }
            if (count == 6){
                break;
            }
        }

        StringBuilder builder = new StringBuilder(town.size());

        for (Character ch : town){
            builder.append(ch);
        }

        return builder.toString();
    }

    private float temperatureParser(String tempMin, String tempMax){
        float tMin = Float.parseFloat(tempMin);
        float tMax = Float.parseFloat(tempMax);
        return (tMin + tMax) / 2;
    }
}
