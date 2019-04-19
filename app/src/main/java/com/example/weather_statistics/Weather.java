package com.example.weather_statistics;

import java.util.Date;

public class Weather {
    private float temperature;
    private Date date;
    private String source;
    private String location;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        if (temperature > -90f & temperature < 57f) {
            this.temperature = temperature;
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
