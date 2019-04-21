package com.example.weather_statistics.date;

import android.provider.BaseColumns;

public class WeatherContract {

    private WeatherContract() {
    };

    public static final class WeatherEntry implements BaseColumns {
        public final static String TABLE_NAME = "weather";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EFFECTIVE_DATE = "effectiveData";
        public final static String COLUMN_DATE = "data";
        public final static String COLUMN_TEMPERATURE = "temperature";
        public final static String COLUMN_LOCATION = "location";
        public final static String COLUMN_SOURCE = "source";

        public static final String SOURCE_DEFAULT = "OpenWeatherMap";
    }
}
