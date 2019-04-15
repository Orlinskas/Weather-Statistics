package com.example.weather_statistics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        textViewApi1.setText((int)getApi(Constants.SINOPTIK_UA));
        textViewApi2.setText((int)getApi(Constants.GOOGLE_TEMPERATURE));
        textViewApi3.setText((int)getApi(Constants.ZALUPA_RU));
    }
    
    public byte getApi(String API_NAME){
        byte temperature = 0;
        //код работы с АПИ
        return temperature;
    }
}
