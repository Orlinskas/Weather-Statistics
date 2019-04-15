package com.example.weather_statistics;

import java.util.Date;

public interface WeatherRepositoryInterface {
    public Weather findWeatherByDate (Date date);

}
