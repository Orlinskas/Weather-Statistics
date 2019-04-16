package com.example.weather_statistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

import static com.example.weather_statistics.utils.NetworkUtils.generateRequestAccuweather;
import static com.example.weather_statistics.utils.NetworkUtils.generateRequestOpenweathermap;

public class MainActivity extends AppCompatActivity {
    private TextView textViewApi1, textViewApi2, textViewApi3;
    private Button buttonGetApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewApi1 = findViewById(R.id.activity_main_et_api1);
        textViewApi2 = findViewById(R.id.activity_main_et_api2);
        textViewApi3 = findViewById(R.id.activity_main_et_api3);

    }

    public void onClickButtonGetApi(View view) {
        URL check1 = generateRequestAccuweather(Constants.ACCUWEATHER_KHARKIV_ID);
        URL check2 = generateRequestOpenweathermap(Constants.OPENWEATHERMAP_KHARKIV_ID);
        textViewApi1.setText(check1.toString());
        textViewApi2.setText(check2.toString());
        //textViewApi3.setText((int)getApi(Constants.ZALUPA_RU));
    }


}
