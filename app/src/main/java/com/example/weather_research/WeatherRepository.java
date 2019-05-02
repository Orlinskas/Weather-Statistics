package com.example.weather_research;

import com.example.weather_research.date.DatabaseAdapter;
import com.example.weather_research.date.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WeatherRepository implements WeatherRepositoryInterface {

    private DatabaseAdapter database;
    private ArrayList<Weather> weathers = new ArrayList<>();


    @Override
    public Weather findWeatherByDate(String tableName, String needLocation, String needEffectiveDate, String date) {

        try {
            database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            weathers = database.getWeathers(needEffectiveDate, tableName);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (Weather weather : weathers){
                String weatherLocation = weather.getLocation();

                if(weather.getDate().equals(date) & weatherLocation.equals(needLocation)) {
                    return weather;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Weather("not found", "not found",0.0f, "not", "not" );
    }

    public ArrayList<Weather> findWeathers(String tableName){

        try {
            database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            weathers = database.getWeathers(tableName);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weathers;
    }

    public ArrayList<Weather> findWeathers(String tableName, String location){

        try {
            database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            weathers = database.getWeathers(tableName);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String location = getLocationFromTownName(location, tableName);
        Iterator <Weather> weatherIterator = weathers.iterator();

        try {
            while(weatherIterator.hasNext()) {
                Weather weather = weatherIterator.next();
                if (!weather.getLocation().equals(location)){
                    weatherIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weathers;
    }

    public ArrayList<Weather> findWeathers(String tableName, String location, String needEffectiveData){

        try {
            database = new DatabaseAdapter(AppContext.getContext());
            database.open();
            weathers = database.getWeathers(needEffectiveData, tableName);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String location = getLocationFromTownName(location, tableName);
        Iterator <Weather> weatherIterator = weathers.iterator();

        try {
            while(weatherIterator.hasNext()) {
                Weather weather = weatherIterator.next();
                if (!weather.getLocation().equals(location) & !weather.getEffectiveDate().equals(needEffectiveData)){
                    weatherIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weathers;
    }

    public ArrayList<String> findAllEffectiveDates(String tableName){
        ArrayList<String> allEffectiveDates = new ArrayList<>();
        database = new DatabaseAdapter(AppContext.getContext());
        ArrayList<Weather> weathers = new ArrayList<>();

        database.open();
        weathers = database.getWeathers(tableName);
        database.close();

        for (Weather weather : weathers){
             allEffectiveDates.add(weather.getEffectiveDate());
        }

        Set<String> set = new HashSet<>(allEffectiveDates);
        allEffectiveDates.clear();
        allEffectiveDates.addAll(set);

        return allEffectiveDates;
    }

    public ArrayList<String> findAllDates(String tableName, String location, String needEffectiveDates){
        ArrayList<String> allDates = new ArrayList<>();
        database = new DatabaseAdapter(AppContext.getContext());
        ArrayList<Weather> weathers = new ArrayList<>();

        database.open();
        weathers = database.getWeathers(tableName);
        database.close();

        for (Weather weather : weathers){
            if (weather.getEffectiveDate().equals(needEffectiveDates) &
                    weather.getLocation().equals(getLocationFromTownName(location, tableName)))

                allDates.add(weather.getDate());
        }

        return allDates;
    }

    public static String getTownNameFromDataLocation(String location){

            switch (location) {
                case "Kharkiv":
                    return "Харьков";
                case "Moskva":
                    return "Москва";
                case "Vilnius":
                    return "Вильнюс";
                case "Lublin":
                    return "Люблин";
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

    public static String getLocationFromTownName(String location, String tableName){

        if (tableName.equals(DatabaseHelper.TABLE_ACCU_WEATHER)) {
            switch (location) {
                case "Харьков":
                    return "kharkiv";
                case "Москва":
                    return "moscow";
                case "Вильнюс":
                    return "vilnius";
                case "Люблин":
                    return "lublin";
                default:
                    return "not found";
            }

        }

        if (tableName.equals(DatabaseHelper.TABLE_OPEN_WEATHER)){
            switch (location) {
                case "Харьков":
                    return "Kharkiv";
                case "Москва":
                    return "Moskva";
                case "Вильнюс":
                    return "Vilnius";
                case "Люблин":
                    return "Lublin";
                default:
                    return "not found";
            }
        }

        return "not found";
    }
}
