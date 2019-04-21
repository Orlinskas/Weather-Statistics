package com.example.weather_statistics.date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.weather_statistics.Weather;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_EFFECTIVE_DATE, DatabaseHelper.COLUMN_DATE
                ,DatabaseHelper.COLUMN_TEMPERATURE, DatabaseHelper.COLUMN_LOCATION, DatabaseHelper.COLUMN_SOURCE};
        return  database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
    }

    public List<Weather> getWeathers(){  //НЕ ГОТОВ! А может и готов...
        ArrayList<Weather> weathers = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
                String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));

                weathers.add(new Weather(id, effectiveDate, date, temperature, location, source));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  weathers;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_NAME);
    }

    public Weather getWeather(int id){
        Weather weather = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
            String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
            String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));
            weather = new Weather(id, effectiveDate, date, temperature, location, source);
        }
        cursor.close();
        return  weather;
    }

    public long insert(Weather weather){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EFFECTIVE_DATE, weather.getEffectiveDate());
        cv.put(DatabaseHelper.COLUMN_DATE, weather.getDate());
        cv.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getTemperature());
        cv.put(DatabaseHelper.COLUMN_LOCATION, weather.getLocation());
        cv.put(DatabaseHelper.COLUMN_SOURCE, weather.getSource());
        return  database.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    public long delete(int userId){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};

        return database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);
    }

    public long update(Weather weather){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(weather.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EFFECTIVE_DATE, weather.getEffectiveDate());
        cv.put(DatabaseHelper.COLUMN_DATE, weather.getDate());
        cv.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getTemperature());
        cv.put(DatabaseHelper.COLUMN_LOCATION, weather.getLocation());
        cv.put(DatabaseHelper.COLUMN_SOURCE, weather.getSource());
        return database.update(DatabaseHelper.TABLE_NAME, cv, whereClause, null);
    }
}

