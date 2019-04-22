package com.example.weather_statistics;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weather_statistics.date.DatabaseAdapter;
import com.example.weather_statistics.date.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3, textViewApi4, textViewApi5
            , tvData1, tvData2, tvData3, tvData4, tvData5, tvData6;
    private Button buttonGetApi, buttonData;
    private ProgressBar progressBarHorizontal;
    private Date effectiveDate = new Date();
    private String date = null;
    private String temperature = null;
    private String sourse = "OpenWeatherMap";
    private String location = null;

    ArrayList<Weather> weathers = new ArrayList<>();

    WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
    WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();

    WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();
    WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();

    DatabaseAdapter database;




    class ApiGetResponse extends AsyncTask <Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonGetApi.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                int value = 0;


                //Закончились вызовы АПИ
                //parserAcuuWeather.parse(requestSenderAccuWeather.requestWeather(Constants.ACCUWEATHER_KHARKIV_ID));
                publishProgress(++value);

                weathers = parserOpenWeather.parse(requestSenderOpenWeather.requestWeather(Constants.OPENWEATHERMAP_KHARKIV_ID));
                publishProgress(++value);

                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            progressBarHorizontal.setProgress(0);

            textViewApi1.setText(weathers.get(1).getEffectiveDate());
            textViewApi2.setText(weathers.get(1).getDate());
            textViewApi3.setText(Float.toString(weathers.get(1).getTemperature()));
            textViewApi4.setText(weathers.get(1).getLocation());
            textViewApi5.setText(weathers.get(1).getSource());

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        progressBarHorizontal.setMax(2);
        database = new DatabaseAdapter(this);
    }

    public void onClickButtonGetApi(View view) {
         new ApiGetResponse().execute();
    }

    public void onClickButtonSaveData(View view) {
        database.open();
        database.insert(weathers.get(1));
        database.close();
    }

    public void onClickButtonGetData(View view) {
        database.open();
        Weather weather = database.getWeather(1);
        //tvData1.setText(weather.getId());
        tvData2.setText(weather.getEffectiveDate());
        tvData3.setText(weather.getDate());
        tvData4.setText(Float.toString(weather.getTemperature()));
        tvData5.setText(weather.getLocation());
        tvData5.setText(weather.getSource());
        database.close();
    }

}
