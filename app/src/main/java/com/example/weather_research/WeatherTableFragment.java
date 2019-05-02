package com.example.weather_research;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherTableFragment extends Fragment implements DataActivity.WeatherFromDataToFragment {
    TextView fragmentId, effectiveDate, date, location, temperature;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_table, container, false);
        fragmentId = view.findViewById(R.id.fragment_weather_table_tv_num);
        effectiveDate = view.findViewById(R.id.fragment_weather_table_tv_efDate);
        date = view.findViewById(R.id.fragment_weather_table_tv_date);
        location = view.findViewById(R.id.fragment_weather_table_tv_location);
        temperature = view.findViewById(R.id.fragment_weather_table_tv_temp);

        return view;
    }

    public void setDataToWeatherFragment(Weather weather){

        try {


            effectiveDate.setText(weather.getEffectiveDate());
          //  date.setText("gdsg");
           // location.setText("asdsdh");
          //  temperature.setText(String.format("%s ^C", Float.toString(weather.getTemperature())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendWeather(Weather weather) {
        try {
            effectiveDate.setText(weather.getEffectiveDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //@Override
    //public void sendData(String data) {
    //    if(data != null)
    //        text.setText(data);
    //}
}

