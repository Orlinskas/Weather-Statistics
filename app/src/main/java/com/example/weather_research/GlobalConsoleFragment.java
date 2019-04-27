package com.example.weather_research;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class GlobalConsoleFragment extends Fragment {
    public TextView tvLastErrorText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_console, container, false);
        tvLastErrorText = view.findViewById(R.id.fragment_global_console_tv_last_error_message);
        return view;
    }

    public void setTvLastErrorText(String errorMessage){
        tvLastErrorText.setText(errorMessage);
    }
}
