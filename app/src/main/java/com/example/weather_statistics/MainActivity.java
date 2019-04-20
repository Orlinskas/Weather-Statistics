package com.example.weather_statistics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3, textViewApi4, textViewApi5;
    private Button buttonGetApi;


    class ApiGetResponse extends AsyncTask <Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewApi1.setText("work");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
            WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();

            WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();
            WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();

            parserAcuuWeather.parse(requestSenderAccuWeather.requestWeather(Constants.ACCUWEATHER_KHARKIV_ID));
            parserOpenWeather.parse(requestSenderOpenWeather.requestWeather(Constants.OPENWEATHERMAP_KHARKIV_ID));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textViewApi2.setText("work done");
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
    }

    public void onClickButtonGetApi(View view) {
         new ApiGetResponse().execute();
    }


}
