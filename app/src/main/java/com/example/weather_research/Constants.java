package com.example.weather_research;

public class Constants {
    public final static String COMMON_DATEDATA_FORMAT = "yyyy-MM-dd HH:00";
    public final static String ERROR = "error";

    public final static String ACCUWEATHER_COM = "https://dataservice.accuweather.com/";
    public final static String ACCUWEATHER_FORECAST_5day = "forecasts/v1/daily/5day/";
    public final static String ACCUWEATHER_API_KEY = "GxWxbGf8Mwh74gEwcAtkq6Pfg9lGAiTf";
    public final static String ACCUWEATHER_KHARKIV_ID = "323903";
    public final static String ACCUWEATHER_MOSKOW_ID = "294021";
    public final static String ACCUWEATHER_LUBLIN_ID = "274231";
    public final static String ACCUWEATHER_VILNIUS_ID = "231459";
    public final static String ACCUWEATHER_CHECK = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/323903?apikey=GxWxbGf8Mwh74gEwcAtkq6Pfg9lGAiTf&details=false&metric=true";

    public final static String OPENWEATHERMAP_COM = "http://api.openweathermap.org/";
    public final static String OPENWEATHERMAP_FORECAST_5day = "data/2.5/forecast";
    public final static String OPENWEATHERMAP_API_KEY = "a39b0e16bbd652220c6c82560e6814a6";
    public final static String OPENWEATHERMAP_KHARKIV_ID = "706483";
    public final static String OPENWEATHERMAP_MOSKOW_ID = "1220988";
    public final static String OPENWEATHERMAP_LUBLIN_ID = "765876";
    public final static String OPENWEATHERMAP_VILNIUS_ID = "593116";
    public final static String OPENWEATHERMAP_CHECK = "http://api.openweathermap.org/data/2.5/forecast?id=706483&APPID=a39b0e16bbd652220c6c82560e6814a6&units=metric";

    public static final String KEY_ERROR_BUNDLE = "error message";
    public static final String KEY_LAST_ERROR_BUNDLE = "last error message";

    public static final String ERROR_PARSE = "parsing error";
    public static final String ERROR_CONNECTION = "internet connection error";
    public static final String ERROR_BUILD = "build error";
    public static final String ERROR_DATA = "data error";

    public final static String getTownFromId(String id){

        switch (id){
            case ACCUWEATHER_KHARKIV_ID:
                return "Харьков";
            case OPENWEATHERMAP_KHARKIV_ID:
                return "Харьков";
            case OPENWEATHERMAP_MOSKOW_ID:
                return "Москва";
            case ACCUWEATHER_MOSKOW_ID:
                return "Москва";
            case OPENWEATHERMAP_LUBLIN_ID:
                return "Люблин";
            case ACCUWEATHER_LUBLIN_ID:
                return "Люблин";
            case OPENWEATHERMAP_VILNIUS_ID:
                return "Вильнюс";
            case ACCUWEATHER_VILNIUS_ID:
                return "Вильнюс";
            default:
                return "errorTownId";
        }
    }

}
