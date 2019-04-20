package com.example.weather_statistics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3, textViewApi4, textViewApi5;
    private Button buttonGetApi;
    private ProgressBar progressBarHorizontal;

    WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
    WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();

    WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();
    WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();


    class ApiGetResponse extends AsyncTask <Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewApi1.setText("work");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                int value = 0;

                parserAcuuWeather.parse(requestSenderAccuWeather.requestWeather(Constants.ACCUWEATHER_KHARKIV_ID));
                publishProgress(++value);

                parserOpenWeather.parse(requestSenderOpenWeather.requestWeather(Constants.OPENWEATHERMAP_KHARKIV_ID));
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
            textViewApi2.setText("work done");
            progressBarHorizontal.setProgress(0);
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
        progressBarHorizontal = findViewById(R.id.progressBar2);
        progressBarHorizontal.setMax(2);
    }

    public void onClickButtonGetApi(View view) {
         new ApiGetResponse().execute();
    }


}
