package com.example.weather_research;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GlobalConsoleFragment extends Fragment {
    public TextView tvErrorText, tvLastErrorText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_console, container, false);

        tvErrorText = view.findViewById(R.id.fragment_global_console_tv_error_text);
        tvLastErrorText = view.findViewById(R.id.fragment_global_console_tv_last_error_message);
        tvLastErrorText.setText(getArguments().getString(Constants.KEY_LAST_ERROR_BUNDLE));

        return view;
    }

    public void addTextInGlobalConsole(String errorMessage){
        tvErrorText.setText(String.format("%s%s", errorMessage, getClass().getName()));

    }
}
