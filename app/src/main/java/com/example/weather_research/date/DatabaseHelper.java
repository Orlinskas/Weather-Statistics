package com.example.weather_research.date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_OPEN_WEATHER = "OpenWeather";
    public static final String TABLE_ACCU_WEATHER = "AccuWeather";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EFFECTIVE_DATE = "effectiveData";
    public static final String COLUMN_DATE = "data";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_SOURCE = "source";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_OPEN_WEATHER = "CREATE TABLE " + TABLE_OPEN_WEATHER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EFFECTIVE_DATE + " TEXT NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_TEMPERATURE + " REAL NOT NULL DEFAULT 0.0, "
                + COLUMN_LOCATION + " TEXT NOT NULL, "
                + COLUMN_SOURCE + " TEXT NOT NULL );";

        String SQL_CREATE_TABLE_ACCU_WEATHER = "CREATE TABLE " + TABLE_ACCU_WEATHER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EFFECTIVE_DATE + " TEXT NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_TEMPERATURE + " REAL NOT NULL DEFAULT 0.0, "
                + COLUMN_LOCATION + " TEXT NOT NULL, "
                + COLUMN_SOURCE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_TABLE_OPEN_WEATHER);
        db.execSQL(SQL_CREATE_TABLE_ACCU_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPEN_WEATHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCU_WEATHER);
        onCreate(db);
    }
}
