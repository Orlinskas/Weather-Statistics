package com.example.weather_statistics;

import java.util.ArrayList;

public interface WeatherSourceResponseParseInterface {
    public ArrayList<Weather> parse(String json);

}

