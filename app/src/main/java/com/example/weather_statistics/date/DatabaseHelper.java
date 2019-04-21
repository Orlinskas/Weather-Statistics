package com.example.weather_statistics.date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weather_statistics.Weather;
import com.example.weather_statistics.date.WeatherContract.WeatherEntry;

public class WeatherSQLiteHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = WeatherSQLiteHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;


    public WeatherSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + WeatherEntry.TABLE_NAME + " ("
                + WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WeatherEntry.COLUMN_EFFECTIVE_DATE + " TEXT NOT NULL, "
                + WeatherEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + WeatherEntry.COLUMN_TEMPERATURE + " REAL NOT NULL DEFAULT 0.0, "
                + WeatherEntry.COLUMN_LOCATION + " TEXT NOT NULL, "
                + WeatherEntry.COLUMN_SOURCE + " TEXT NOT NULL );";


        db.execSQL(SQL_CREATE_GUESTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ WeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
