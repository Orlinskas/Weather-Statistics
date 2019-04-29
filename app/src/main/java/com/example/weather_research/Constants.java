package com.example.weather_research;

import com.example.weather_research.date.DatabaseHelper;

public class Constants {

    public final static String YYYY_MM_DD_HH_00 = "yyyy-MM-dd HH:00";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public final static String [] TABLENAMES = {DatabaseHelper.TABLE_ACCU_WEATHER, DatabaseHelper.TABLE_OPEN_WEATHER};

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

    public static final String ERROR_PARSE = "parsing error";
    public static final String ERROR_CONNECTION = "internet connection error";
    public static final String ERROR_SET = "set variable error";
    public static final String ERROR_DATA = "data error";
    public static final String ERROR_JSON = "json error";
    public static final String ERROR_URL = "url error";
    public static final String ERROR_ACTIVITY = "activity error";
    public final static String ERROR = "error";
    public static final String ERROR_NULL = "not error message";
    public static final String ERROR_DATA_EMPTY = "data empty";
    public static final String ERROR_THREAD = "thread error";

    public static final int IDD_DELETE = 0;

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

    public final static String getTownFromName(String id, String tableName){

        if(tableName.equals(DatabaseHelper.TABLE_OPEN_WEATHER)){
            switch (id) {
                case "Kharkiv":
                    return "Харьков";
                case "Moskva":
                    return "Москва";
                case "Vilnius":
                    return "Вильнюс";
                case "Lublin":
                    return "Люблин";
                default: return "not found";
            }
        }
        if(tableName.equals(DatabaseHelper.TABLE_ACCU_WEATHER)) {
            switch (id) {
                case "kharkiv":
                    return "Харьков";
                case "moscow":
                    return "Москва";
                case "vilnius":
                    return "Вильнюс";
                case "lublin":
                    return "Люблин";
                default: return "not found";
            }
        }
        else {
            return "not found";
        }
    }

}
