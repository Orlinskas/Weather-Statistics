package com.example.weather_statistics;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    private float temperature;
    private Date date;
    private Date effectiveDate;
    private String source;
    private String location;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH", Locale.ENGLISH);
        this.date = simpleDateFormat.parse(simpleDateFormat.format(date));
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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH", Locale.ENGLISH);
        this.effectiveDate = simpleDateFormat.parse(simpleDateFormat.format(effectiveDate));
    }
}
