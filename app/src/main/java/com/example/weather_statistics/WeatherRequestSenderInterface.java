package com.example.weather_statistics;

import java.util.Date;

public interface WeatherRequestSenderInterface {
    public String requestWeather(String location, Date date);

}
