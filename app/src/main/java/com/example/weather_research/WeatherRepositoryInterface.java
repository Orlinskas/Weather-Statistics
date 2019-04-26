package com.example.weather_research;

import java.util.Date;

public interface WeatherRepositoryInterface {
    public Weather findWeatherByDate (Date date, Date effectiveDate);

}
