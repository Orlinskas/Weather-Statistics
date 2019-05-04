package com.example.weather_research;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherTableFragment extends Fragment {

    TextView fragmentId, effectiveDate, fragmentDate, location, temperature;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_table, container, false);
        fragmentId = view.findViewById(R.id.fragment_weather_table_tv_num);
        effectiveDate = view.findViewById(R.id.fragment_weather_table_tv_efDate);
        fragmentDate = view.findViewById(R.id.fragment_weather_table_tv_date);
        location = view.findViewById(R.id.fragment_weather_table_tv_location);
        temperature = view.findViewById(R.id.fragment_weather_table_tv_temp);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = bundle.getInt("id");
            String efDate = bundle.getString("efDate");
            String date = bundle.getString("fragmentDate");
            String town = bundle.getString("location");
            float temp = bundle.getFloat("temp");

            fragmentId.setText(String.valueOf(id));
            effectiveDate.setText(efDate);
            fragmentDate.setText(date);
            location.setText(town);
            temperature.setText(String.valueOf(temp));
        }
        else {
            location.setText("ASSHOLE");
        }

        return view;
    }

}

