package com.example.weather_research;

public class Precipitation extends Weather {
    private float snow_mm = 0.0f;
    private float rain_mm = 0.0f;
    private boolean snow = false;
    private boolean rain = false;
    private boolean precipitation = false;

    public  Precipitation (float snow_mm , float rain_mm){
        this.snow_mm = snow_mm;
        this.rain_mm = rain_mm;
        if (snow_mm > 0.0f){
            this.snow = true;
            this.precipitation = true;
        }
        if (rain_mm > 0.0f){
            this.rain = true;
            this.precipitation = true;
        }
    }

    public  Precipitation (float rain_mm){
        this.rain_mm = rain_mm;
        if (rain_mm > 0.0f){
            this.rain = true;
            this.precipitation = true;
        }
    }

    public Precipitation(){}

    public float getSnow_mm() {
        return snow_mm;
    }

    public void setSnow_mm(float snow_mm) {
        this.snow_mm = snow_mm;
    }

    public float getRain_mm() {
        return rain_mm;
    }

    public void setRain_mm(float rain_mm) {
        this.rain_mm = rain_mm;
    }

    public boolean isSnow() {
        return snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public boolean isPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(boolean precipitation) {
        this.precipitation = precipitation;
    }
}
