package com.example.weather_statistics.date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EFFECTIVE_DATE = "effectiveData";
    public static final String COLUMN_DATE = "data";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_SOURCE = "source";

    public static final String SOURCE_DEFAULT_OPENWEATHER = "OpenWeatherMap";
    public static final String SOURCE_DEFAULT_ACCUWEATHER = "AccuWeather";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EFFECTIVE_DATE + " TEXT NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_TEMPERATURE + " REAL NOT NULL DEFAULT 0.0, "
                + COLUMN_LOCATION + " TEXT NOT NULL, "
                + COLUMN_SOURCE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
