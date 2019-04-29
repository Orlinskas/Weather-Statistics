package com.example.weather_research;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.weather_research.date.DatabaseHelper;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        showTitle();

        AppContext.setContext(this);

        Weather weather;

        //WeatherRepository testTableData = new WeatherRepository();
        //weather = testTableData.findWeatherByDate(DatabaseHelper.TABLE_ACCU_WEATHER, "2019-04-29 15:00", "2019-04-30 07:00", "Москва");
        //float temp = weather.getTemperature();
        //String tempTest = String.valueOf(temp);
        //Toast toast = Toast.makeText(getApplicationContext(),
        //        tempTest,
        //        Toast.LENGTH_SHORT);
        //toast.show();

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

        tabSpec.setContent(R.id.tab_host_ll_first_button);
        tabSpec.setIndicator("Кот");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab_host_ll_second_button);
        tabSpec.setIndicator("Кошка");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab_host_ll_third_button);
        tabSpec.setIndicator("Котёнок");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }


}