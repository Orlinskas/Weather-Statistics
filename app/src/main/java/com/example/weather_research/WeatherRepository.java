package com.example.weather_research;

import com.example.weather_research.date.DatabaseAdapter;

import java.util.ArrayList;

public class WeatherRepository implements WeatherRepositoryInterface {

    private DatabaseAdapter database;
    private ArrayList<Weather> weathers = new ArrayList<>();

    @Override
    public Weather findWeatherByDate(String tableName, String effectiveDate, String date, String location) {

        try {
            database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            weathers = database.getWeathers(effectiveDate, tableName);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Weather weather : weathers){
                String weatherLocation = Constants.getTownFromName(weather.getLocation(), tableName);

                if(weather.getDate().equals(date) & weatherLocation.equals(location)) {
                    return weather;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Weather("not found", "not found",0.0f, "not", "not" );
    }
}
