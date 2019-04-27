package com.example.weather_research;


public class Weather {
    private String effectiveDate;
    private String date;
    private float temperature;
    private String location;
    private String source;

    public Weather(String effectiveDate, String date, float temperature, String location, String source){
        this.effectiveDate = effectiveDate;
        this.date = date;
        this.temperature = temperature;
        this.location = location;
        this.source = source;
    }

    public Weather() {
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        if (temperature > -90f & temperature < 57f) {
           // DecimalFormat decimalFormat = new DecimalFormat("#.0");
            this.temperature = temperature;
        }
        else {
            this.temperature = 0.0f;
        }
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

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
