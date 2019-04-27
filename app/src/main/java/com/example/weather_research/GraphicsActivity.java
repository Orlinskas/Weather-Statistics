package com.example.weather_research;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_research.date.DatabaseAdapter;
import com.example.weather_research.date.DatabaseHelper;

import java.util.ArrayList;


public class GraphicsActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3, textViewApi4, textViewApi5
            , tvData1, tvData2, tvData3, tvData4, tvData5, tvData6;
    private Button buttonGetApi, buttonData;
    private ProgressBar progressBarHorizontal;

    DatabaseAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        textViewApi1 = findViewById(R.id.activity_main_et_api1);
        textViewApi2 = findViewById(R.id.activity_main_et_api2);
        textViewApi3 = findViewById(R.id.activity_main_et_api3);
        textViewApi4 = findViewById(R.id.activity_main_et_api4);
        textViewApi5 = findViewById(R.id.activity_main_et_api5);
        buttonGetApi = findViewById(R.id.activity_main_btn);
        buttonData = findViewById(R.id.btn_saveData);
        tvData1 = findViewById(R.id.activity_main_et_data1);
        tvData2 = findViewById(R.id.activity_main_et_data2);
        tvData3 = findViewById(R.id.activity_main_et_data3);
        tvData4 = findViewById(R.id.activity_main_et_data4);
        tvData5 = findViewById(R.id.activity_main_et_data5);
        tvData6 = findViewById(R.id.activity_main_et_data6);
        progressBarHorizontal = findViewById(R.id.progressBar2);
        progressBarHorizontal.setMax(100);
        database = new DatabaseAdapter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickMenuItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_graphics:
                ActivityOpener.openActivity(GraphicsActivity.this, GraphicsActivity.class);
                break;
            case R.id.action_connection:
                ActivityOpener.openActivity(GraphicsActivity.this, ConnectionActivity.class);
                break;
            case R.id.action_data:
                ActivityOpener.openActivity(GraphicsActivity.this, DataActivity.class);
                break;
            case R.id.action_settings:
                ActivityOpener.openActivity(GraphicsActivity.this, SettingsActivity.class);
                break;
            case R.id.action_help:
                ActivityOpener.openActivity(GraphicsActivity.this, HelpActivity.class);
                break;
        }
    }

    class ApiGetDataInsertAccuWeather extends AsyncTask <String, Integer, Void>{
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
        WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonData.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Wait please...", Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected Void doInBackground(String... locationID) {
            int countID, countProgress = 1;

            for (countID = 0; countID < locationID.length; countID++){
                weathers = parserAcuuWeather
                        .parse(requestSenderAccuWeather.requestWeather(locationID[countID]));
                ++countProgress;
                publishProgress(countProgress);

                for (Weather weather : weathers ){
                    database.open();
                    database.insert(weather, DatabaseHelper.TABLE_ACCU_WEATHER);
                    database.close();
                    ++countProgress;
                    publishProgress(countProgress);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBarHorizontal.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            buttonData.setVisibility(View.VISIBLE);
            progressBarHorizontal.setProgress(progressBarHorizontal.getMax());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Data collected", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


    class ApiGetDataInsertOpenWeather extends AsyncTask <String, Integer, Void>{
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();
        WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonGetApi.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Try..wait", Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected Void doInBackground(String... locationID) {
            int countID, countProgress = 1;

            for (countID = 0; countID < locationID.length; countID++){
                weathers = parserOpenWeather
                        .parse(requestSenderOpenWeather.requestWeather(locationID[countID]));
                ++countProgress;
                publishProgress(countProgress);

                for (Weather weather : weathers ){
                    database.open();
                    database.insert(weather, DatabaseHelper.TABLE_OPEN_WEATHER);
                    database.close();
                    ++countProgress;
                    publishProgress(countProgress);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBarHorizontal.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            buttonGetApi.setVisibility(View.VISIBLE);
            progressBarHorizontal.setProgress(progressBarHorizontal.getMax());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Data collected", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onClickButtonGetApi(View view) {
         new ApiGetDataInsertOpenWeather().execute(Constants.OPENWEATHERMAP_KHARKIV_ID,
                 Constants.OPENWEATHERMAP_MOSKOW_ID);
    }

    public void onClickButtonSaveData(View view) {
        new ApiGetDataInsertAccuWeather().execute(Constants.ACCUWEATHER_KHARKIV_ID);
    }

    public void onClickButtonGetData(View view) {

        Context context = GraphicsActivity.this;
        Class settingsActivityClass = SettingsActivity.class;
        Intent settingsActivityIntent = new Intent(context, settingsActivityClass);
        startActivity(settingsActivityIntent);

    }

}
