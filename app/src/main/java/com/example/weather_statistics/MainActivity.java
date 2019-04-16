package com.example.weather_statistics;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static com.example.weather_statistics.utils.NetworkUtils.generateURLAccuweather;
import static com.example.weather_statistics.utils.NetworkUtils.generateURLOpenweathermap;
import static com.example.weather_statistics.utils.NetworkUtils.getResponceFromURL;

public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3;
    private Button buttonGetApi;

    class ApiGetResponce extends AsyncTask <URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            String responce = null;

            try {
                responce = getResponceFromURL(urls[0]);
            } catch (IOException e){
                e.printStackTrace();
            }

            return responce;
        }

        @Override
        protected void onPostExecute(String responce){
            textViewApi1.setText(responce);
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
        URL check1 = generateURLAccuweather(Constants.ACCUWEATHER_KHARKIV_ID);
        //URL check2 = generateURLOpenweathermap(Constants.OPENWEATHERMAP_KHARKIV_ID);
        new ApiGetResponce().execute(check1);
    }


}
