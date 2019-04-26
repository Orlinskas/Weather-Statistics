package com.example.weather_research;

import java.util.ArrayList;

public interface WeatherSourceResponseParseInterface {
    public ArrayList<Weather> parse(String json);

}

