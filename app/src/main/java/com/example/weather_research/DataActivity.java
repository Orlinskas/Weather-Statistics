package com.example.weather_research;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_research.date.DatabaseHelper;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    Spinner spinnerApi, spinnerLocation;
    String apiName, locationName, needEffectiveDate, needDate;
    LinearLayout linearLayoutEffectiveDates, linearLayoutDates;

    WeatherRepository weatherRepository = new WeatherRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        showTitle();

        linearLayoutEffectiveDates = findViewById(R.id.tab_host_ll_effectiveDate_button);
        linearLayoutDates = findViewById(R.id.tab_host_ll_date_button);
        spinnerApi = findViewById(R.id.activity_data_ll_spiner_ApiName);
        spinnerLocation = findViewById(R.id.activity_data_ll_spiner_LocationName);

        apiName = spinnerApi.getSelectedItem().toString();
        locationName = spinnerLocation.getSelectedItem().toString();


        AppContext.setContext(this);

        showAllEffectiveDates();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickMenuItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_graphics:
                ActivityOpener.openActivity(getApplicationContext(), GraphicsActivity.class);
                break;
            case R.id.action_connection:
                ActivityOpener.openActivity(getApplicationContext(), ConnectionActivity.class);
                break;
            case R.id.action_data:
                ActivityOpener.openActivity(getApplicationContext(), DataActivity.class);
                break;
            case R.id.action_settings:
                ActivityOpener.openActivity(getApplicationContext(), SettingsActivity.class);
                break;
            case R.id.action_help:
                ActivityOpener.openActivity(getApplicationContext(), HelpActivity.class);
                break;
        }
    }

    public void showTitle(){
        setTitle("База данных");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.tab_host_ll_Api_button);
        tabSpec.setIndicator("Api");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab_host_ll_location_button);
        tabSpec.setIndicator("Город");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab_host_ll_effectiveDate_button);
        tabSpec.setIndicator("Время Api");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab_host_ll_date_button);
        tabSpec.setIndicator("Дата");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    public void showAllEffectiveDates(){
        ArrayList<String> effectiveDates;

        effectiveDates = weatherRepository.findAllEffectiveDates(DatabaseHelper.TABLE_OPEN_WEATHER);

        for (int i = 0; i< effectiveDates.size(); i++){
            final Button effectiveDateBtn = new Button(this);
            effectiveDateBtn.setTextColor(Color.BLACK);
            effectiveDateBtn.setTextSize(18f);
            effectiveDateBtn.setText(effectiveDates.get(i));
            effectiveDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    needEffectiveDate = effectiveDateBtn.getText().toString();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            effectiveDateBtn.getText().toString(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            linearLayoutEffectiveDates.addView(effectiveDateBtn);
        }
    }



}