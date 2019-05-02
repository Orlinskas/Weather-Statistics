package com.example.weather_research;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



import com.example.weather_research.date.DatabaseHelper;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    Spinner spinnerApi, spinnerLocation;
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
        spinnerApi = findViewById(R.id.activity_data_ll_spiner_ApiName);
        spinnerLocation = findViewById(R.id.activity_data_ll_spiner_LocationName);

        apiName = spinnerApi.getSelectedItem().toString();
        locationName = spinnerLocation.getSelectedItem().toString();
        needEffectiveDate = "All";

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
        String tableName = spinnerApi.getSelectedItem().toString(); //нужно добавить слушатель спиннера

        effectiveDates = weatherRepository.findAllEffectiveDates(DatabaseHelper.TABLE_OPEN_WEATHER); //вот тут опасно!

        for (int i = 0; i < effectiveDates.size(); i++) {
            final Button effectiveDateBtn = new Button(this);
            effectiveDateBtn.setTextColor(Color.BLACK);
            effectiveDateBtn.setTextSize(18f);
            effectiveDateBtn.setText(effectiveDates.get(i));
            effectiveDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    needEffectiveDate = effectiveDateBtn.getText().toString();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Выбрано- " + effectiveDateBtn.getText().toString(), Toast.LENGTH_SHORT);
                    toast.show();
                    buildTextInSearchField();
                    showAllDates();
                }
            });
            linearLayoutEffectiveDates.addView(effectiveDateBtn);
        }
    }

    public void showAllDates(){
        ArrayList<String> dates;
        String tableName = spinnerApi.getSelectedItem().toString();
        String location = spinnerLocation.getSelectedItem().toString();
        linearLayoutDates.removeAllViews();

        if (tableName.equals("All")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Сначала выбирете Aпи" , Toast.LENGTH_SHORT);
            toast.show();
        }
        if (location.equals("All")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Сначала выбирете город" , Toast.LENGTH_SHORT);
            toast.show();
        }
        if (needEffectiveDate.equals("All")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Сначала выбирете дату взятия данных" , Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
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
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Выбрано- " + dateBtn.getText().toString(), Toast.LENGTH_SHORT);
                        toast.show();
                        buildTextInSearchField();
                    }
                });
                linearLayoutDates.addView(dateBtn);
            }
        }
    }

    public void buildTextInSearchField(){
        searchField = findViewById(R.id.activity_data_tv_search_in);
        apiName = spinnerApi.getSelectedItem().toString();
        locationName = spinnerLocation.getSelectedItem().toString();
        searchField.setText(String.format("  %s %s (%s)-(%s)", apiName, locationName, needEffectiveDate, needDate));
    }

    public Weather collectWeatherDataWithSearchField(){
        Weather weather;
        locationName = WeatherRepository.getLocationFromTownName(locationName, apiName);
        weather = weatherRepository.findWeatherByDate(apiName, locationName, needEffectiveDate, needDate);

        return weather;
    }

    public void onClickSearchButton(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WeatherTableFragment weatherTableFragment = new WeatherTableFragment();
        WeatherFromDataToFragment weatherFromDataToFragment = (WeatherFromDataToFragment) weatherTableFragment;

        Weather weather = collectWeatherDataWithSearchField();

        fragmentTransaction.add(R.id.fragment_container, weatherTableFragment);
        fragmentTransaction.commit();

       // weatherTableFragment.setDataToWeatherFragment(weather);
        weatherFromDataToFragment.sendWeather(weather);
    }

    public interface WeatherFromDataToFragment {
        void sendWeather(Weather weather);
    }
}