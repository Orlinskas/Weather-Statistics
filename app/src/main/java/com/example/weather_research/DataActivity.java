package com.example.weather_research;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.weather_research.date.DatabaseHelper;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    String apiName, locationName, needEffectiveDate, needDate;
    LinearLayout linearLayoutEffectiveDates, linearLayoutDates;
    TextView searchField;

    WeatherRepository weatherRepository = new WeatherRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        showTitle();

        linearLayoutEffectiveDates = findViewById(R.id.tab_host_ll_effectiveDate_button);
        linearLayoutDates = findViewById(R.id.tab_host_ll_date_button);

        apiName = "All";
        locationName = "All";
        needEffectiveDate = "All";
        needDate = "All";

        AppContext.setContext(this);
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

    public void onClickApiName(View view) {
        switch (view.getId()){
            case R.id.activity_data_btn_api_all:
                apiName = "All";
                break;
            case R.id.activity_data_btn_api_open_weather:
                apiName = DatabaseHelper.TABLE_OPEN_WEATHER;
                break;
            case R.id.activity_data_btn_api_accu_weather:
                apiName = DatabaseHelper.TABLE_ACCU_WEATHER;
                break;
        }

        buildTextInSearchField();
    }

    public void onClickApiLocation(View view) {
        if (!apiName.equals("All")){

            switch (view.getId()){

                case R.id.activity_data_btn_location_kh:
                    locationName = "Харьков";
                    break;
                case R.id.activity_data_btn_location_msc:
                    locationName = "Москва";
                    break;
                case R.id.activity_data_btn_location_lb:
                    locationName = "Люблин";
                    break;
                case R.id.activity_data_btn_location_vl:
                    locationName = "Вильнюс";
                    break;
            }

            locationName = WeatherRepository.getLocationFromTownName(locationName, apiName);
            buildTextInSearchField();
            showAllEffectiveDates();

        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Сначала выбирете Aпи" , Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void showAllEffectiveDates(){
        ArrayList<String> effectiveDates;
        String tableName = apiName;

        effectiveDates = weatherRepository.findAllEffectiveDates(tableName);

        for (int i = 0; i < effectiveDates.size(); i++) {
            final Button effectiveDateBtn = new Button(this);
            effectiveDateBtn.setTextColor(Color.BLACK);
            effectiveDateBtn.setTextSize(18f);
            effectiveDateBtn.setText(effectiveDates.get(i));
            effectiveDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    needEffectiveDate = effectiveDateBtn.getText().toString();
                    buildTextInSearchField();
                    showAllDates();
                }
            });
            linearLayoutEffectiveDates.addView(effectiveDateBtn);
        }
    }

    public void showAllDates(){
        ArrayList<String> dates;
        String tableName = apiName;
        String location = locationName;
        linearLayoutDates.removeAllViews();

        dates = weatherRepository.findAllDates(tableName, location, needEffectiveDate);

            for (int i = 0; i < dates.size(); i++) {
                final Button dateBtn = new Button(this);
                dateBtn.setTextColor(Color.BLACK);
                dateBtn.setTextSize(18f);
                dateBtn.setText(dates.get(i));
                dateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        needDate = dateBtn.getText().toString();
                        buildTextInSearchField();
                    }
                });
                linearLayoutDates.addView(dateBtn);
            }
    }

    public void buildTextInSearchField(){
        searchField = findViewById(R.id.activity_data_tv_search_in);
        searchField.setText(String.format("  %s %s (%s) - (%s)", apiName, locationName, needEffectiveDate, needDate));
    }

    public void onClickSearchButton(View view) {
        ArrayList<Weather> weathers = new ArrayList<>();

        switch (situationNumber()) {
            case 1:
                weathers = weatherRepository.findWeathers(apiName);
                break;
            case 2:
                weathers = weatherRepository.findWeathers(apiName, locationName);
                break;
            case 3:
                weathers = weatherRepository.findWeathers(apiName, locationName, needEffectiveDate);
                break;
            case 4:
                weathers = weatherRepository.findWeathers(apiName, locationName, needEffectiveDate, needDate);
                break;

        }

        if (weathers.size() > 0){

            for (Weather weather : weathers){
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                WeatherTableFragment weatherTableFragment = new WeatherTableFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", weathers.indexOf(weather));
                bundle.putString("efDate", weather.getEffectiveDate());
                bundle.putString("fragmentDate", weather.getDate());
                bundle.putString("location", weather.getLocation());
                bundle.putFloat("temp", weather.getTemperature());
                weatherTableFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.fragment_container, weatherTableFragment);
                fragmentTransaction.commit();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public int situationNumber(){
        ArrayList<String> countOfDatas = new ArrayList<>();

        if(!apiName.equals("All")){
            countOfDatas.add(apiName);
        }

        if(!locationName.equals("All")){
            countOfDatas.add(locationName);
        }

        if(!needEffectiveDate.equals("All")){
            countOfDatas.add(needEffectiveDate);
        }

        if(!needDate.equals("All")){
            countOfDatas.add(needDate);
        }

        return countOfDatas.size();
    }


}