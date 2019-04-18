package com.example.weather_statistics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import static com.example.weather_statistics.utils.JsonUtils.getTemperatureAccuWeather;
import static com.example.weather_statistics.utils.JsonUtils.getTemperatureOpenWeather;
import static com.example.weather_statistics.utils.NetworkUtils.generateURLAccuWeather;
import static com.example.weather_statistics.utils.NetworkUtils.generateURLOpenWeather;
import static com.example.weather_statistics.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3;
    private Button buttonGetApi;


    class ApiGetResponce extends AsyncTask <URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;

            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response){
            textViewApi1.setText(getTemperatureAccuWeather(response));
            textViewApi2.setText(getTemperatureOpenWeather(response));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewApi1 = findViewById(R.id.activity_main_et_api1);
        textViewApi2 = findViewById(R.id.activity_main_et_api2);
        textViewApi3 = findViewById(R.id.activity_main_et_api3);

    }

    public void onClickButtonGetApi(View view) {
        URL check1 = generateURLAccuWeather(Constants.ACCUWEATHER_KHARKIV_ID);
        URL check2 = generateURLOpenWeather(Constants.OPENWEATHERMAP_KHARKIV_ID);
        new ApiGetResponce().execute(check1);
        new ApiGetResponce().execute(check2);
    }


}
