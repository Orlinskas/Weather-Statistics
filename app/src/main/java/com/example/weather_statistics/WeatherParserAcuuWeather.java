package com.example.weather_statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class WeatherParserAcuuWeather implements WeatherSourceResponseParseInterface {

    @Override
    public ArrayList<Weather> parse(String json){
        ArrayList<Weather> weathers = new ArrayList<>();

        SimpleDateFormat commonFormat = new SimpleDateFormat(Constants.COMMON_DATEDATA_FORMAT, Locale.ENGLISH);
        String effectiveDate = commonFormat.format(new Date());
        String date;
        String temperatureMin;
        String temperatureMax;
        String sourse;
        String link;

        for(int index = 0; index <= 4; index++ ) {
            try {
                JSONObject jsonObjectAccuWeather = new JSONObject(json);

                JSONArray jsonArrayDailyForecast = jsonObjectAccuWeather.getJSONArray("DailyForecasts");
                JSONObject jsonObjectDay = jsonArrayDailyForecast.getJSONObject(index);

                date = jsonObjectDay.getString("Date");
                StringBuilder stringBuffer = new StringBuilder(date);
                date = stringBuffer.delete(16, date.length()).toString();
                SimpleDateFormat dateFormatJson = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
                Date dateJson = dateFormatJson.parse(date);
                date = commonFormat.format(dateJson);

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
                date = Constants.ERROR;
                temperatureMin = Constants.ERROR;
                temperatureMax = Constants.ERROR;
                sourse = Constants.ERROR;
                link = Constants.ERROR;
            } catch (ParseException e) {
                e.printStackTrace();
                date = Constants.ERROR;
                temperatureMin = Constants.ERROR;
                temperatureMax = Constants.ERROR;
                sourse = Constants.ERROR;
                link = Constants.ERROR;
            }

            Weather weather = new Weather();
            weather.setDate(date);
            weather.setEffectiveDate(effectiveDate);
            weather.setTemperature(temperatureParser(temperatureMin, temperatureMax));
            weather.setSource(sourse);
            if (link != null) {
                weather.setLocation(linkParserToLocation(link));
            }
            else {
                weather.setLocation(Constants.ERROR);
            }
            weathers.add(weather);
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
