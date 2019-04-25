package com.example.weather_statistics;

import android.content.Context;
import android.content.Intent;

import static android.support.v4.content.ContextCompat.startActivity;

public class ActivityOpener {

    public static void openActivity(Context contextThisClass, Class needClass ){
        Intent intent = new Intent(contextThisClass, needClass);
        startActivity(contextThisClass, intent, null);
    }
}
