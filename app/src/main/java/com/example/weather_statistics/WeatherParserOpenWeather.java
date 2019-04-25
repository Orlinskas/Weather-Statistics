package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class WeatherParserOpenWeather implements WeatherSourceResponseParseInterface {

    @Override
    public ArrayList<Weather> parse(String json){
        ArrayList<Weather> weathers = new ArrayList<>();

        SimpleDateFormat commonFormat = new SimpleDateFormat(Constants.COMMON_DATEDATA_FORMAT, Locale.ENGLISH);
        String effectiveDate = commonFormat.format(new Date());
        String date;
        String temperature;
        String sourse = "OpenWeatherMap";
        String location;

        for (int index = 0; index <= 39; index++){
            try {
                JSONObject jsonObjectOpenWeather = new JSONObject(json);
                JSONArray jsonArrayDailyForecast = jsonObjectOpenWeather.getJSONArray("list");
                JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(index);

                date = jsonObjectDay.getString("dt_txt");
                SimpleDateFormat dateFormatJson = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date dateJson = dateFormatJson.parse(date);
                date = commonFormat.format(dateJson);

                JSONObject jsonObjectDayTemperature = jsonObjectDay.getJSONObject("main");
                temperature = jsonObjectDayTemperature.getString("temp");

                JSONObject jsonObjectCity = jsonObjectOpenWeather.getJSONObject("city");
                location = jsonObjectCity.getString("name");

            } catch (JSONException e) {
                e.printStackTrace();
                date = Constants.ERROR;
                temperature = Constants.ERROR;
                sourse = Constants.ERROR;
                location = Constants.ERROR;
            } catch (ParseException e) {
                e.printStackTrace();
                date = Constants.ERROR;
                temperature = Constants.ERROR;
                sourse = Constants.ERROR;
                location = Constants.ERROR;
            }

            Weather weather = new Weather();
            weather.setDate(date);
            weather.setEffectiveDate(effectiveDate);
            if (temperature != null) {
                weather.setTemperature(Float.parseFloat(temperature));
            }
            else {
                weather.setTemperature(0.0f);
            }
            weather.setSource(sourse);
            weather.setLocation(location);
            weathers.add(weather);
        }

        return weathers;
    }
}
