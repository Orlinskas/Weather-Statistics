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

    private Cursor getAllEntries(String tableName){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_EFFECTIVE_DATE, DatabaseHelper.COLUMN_DATE
                , DatabaseHelper.COLUMN_TEMPERATURE, DatabaseHelper.COLUMN_LOCATION, DatabaseHelper.COLUMN_SOURCE};
        return  database.query(tableName, columns, null, null, null, null, null);
    }

    public List<Weather> getWeathers(String tableName){
        ArrayList<Weather> weathers = new ArrayList<>();
        Cursor cursor = getAllEntries(tableName);
        if(cursor.moveToFirst()){
            do{
                String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
                String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));

                weathers.add(new Weather(effectiveDate, date, temperature, location, source));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  weathers;
    }

    public List<Weather> getWeathers(String needEffectiveDate, String tableName){
        ArrayList<Weather> weathers = new ArrayList<>();
        Cursor cursor = getAllEntries(tableName);
        if(cursor.moveToFirst()){
            do{
                String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
                String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));

                if(effectiveDate.equals(needEffectiveDate)) {
                    weathers.add(new Weather(effectiveDate, date, temperature, location, source));
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  weathers;
    }

    public long getCount(String tableName){
        return DatabaseUtils.queryNumEntries(database, tableName);
    }

    public Weather getWeather(int id, String tableName){
        Weather weather = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", tableName, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
            String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
            String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));
            weather = new Weather(effectiveDate, date, temperature, location, source);
        }
        cursor.close();
        return  weather;
    }

    public Weather getWeather(String needEffectiveDate, String tableName){
        Weather weather = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", tableName, DatabaseHelper.COLUMN_EFFECTIVE_DATE);
        Cursor cursor = database.rawQuery(query, new String[]{ needEffectiveDate});
        if(cursor.moveToFirst()){
            String effectiveDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EFFECTIVE_DATE));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            float temperature = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEMPERATURE));
            String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION));
            String source = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOURCE));
            weather = new Weather(effectiveDate, date, temperature, location, source);
        }
        cursor.close();
        return  weather;
    }

    public long insert(Weather weather, String tableName){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EFFECTIVE_DATE, weather.getEffectiveDate());
        cv.put(DatabaseHelper.COLUMN_DATE, weather.getDate());
        cv.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getTemperature());
        cv.put(DatabaseHelper.COLUMN_LOCATION, weather.getLocation());
        cv.put(DatabaseHelper.COLUMN_SOURCE, weather.getSource());
        return  database.insert(tableName, null, cv);
    }

    public void delete(int id, String tableName){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        database.delete(tableName, whereClause, whereArgs);
    }

    //Be careful boyy, не работает
    public void deleteAll(SQLiteDatabase database, String tableName){
        long countLong = DatabaseUtils.queryNumEntries(database, tableName);
        int count = (int) countLong;

        for (int i = 1; i == count; i++){
            delete(i, tableName);
        }
    }

    //не использовать пока не будет трай
    public long update(Weather weather, String tableName){
        String whereClause = DatabaseHelper.COLUMN_DATE + "=" + weather.getDate();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EFFECTIVE_DATE, weather.getEffectiveDate());
        cv.put(DatabaseHelper.COLUMN_DATE, weather.getDate());
        cv.put(DatabaseHelper.COLUMN_TEMPERATURE, weather.getTemperature());
        cv.put(DatabaseHelper.COLUMN_LOCATION, weather.getLocation());
        cv.put(DatabaseHelper.COLUMN_SOURCE, weather.getSource());
        return database.update(tableName, cv, whereClause, null);
    }
}

