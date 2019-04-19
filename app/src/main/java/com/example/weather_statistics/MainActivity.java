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
    private TextView textViewApi1, textViewApi2, textViewApi3, textViewApi4, textViewApi5;
    private Button buttonGetApi;


    class ApiGetResponse extends AsyncTask <URL, Void, String>{

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
            WeatherParserAcuuWeather openWeather = new WeatherParserAcuuWeather();

            textViewApi1.setText(openWeather.parse(response).get(1).getDate().toString());
            textViewApi2.setText(Float.toString(openWeather.parse(response).get(1).getTemperature()));
            textViewApi3.setText(openWeather.parse(response).get(1).getSource());
            textViewApi4.setText(openWeather.parse(response).get(1).getLocation());
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
        URL check1 = generateURLAccuWeather(Constants.ACCUWEATHER_KHARKIV_ID);
       // URL check2 = generateURLOpenWeather("706483");
        new ApiGetResponse().execute(check1);
        //new ApiGetResponse().execute(check2);
    }


}
