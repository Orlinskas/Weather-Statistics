package com.example.weather_research;

import java.util.Date;

public interface WeatherRepositoryInterface {
    public Weather findWeatherByDate (String tableName, String effectiveDate, String date, String location);

}
