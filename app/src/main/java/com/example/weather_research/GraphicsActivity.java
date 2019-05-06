package com.example.weather_research;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;


public class GraphicsActivity extends AppCompatActivity {

    Spinner graphicSpinner, locationSpiner;
    private static final String TAG = "GraphicsActivity";
    public int graphicId;
    public String locationName;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    WidgetChartTodayFragment widgetChartTodayFragment = new WidgetChartTodayFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        AppContext.setContext(this);

        graphicSpinner = findViewById(R.id.activity_graphics_spn_graphics_list);
        locationSpiner = findViewById(R.id.activity_graphics_spn_location);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickMenuItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_graphics:
                ActivityOpener.openActivity(GraphicsActivity.this, GraphicsActivity.class);
                break;
            case R.id.action_connection:
                ActivityOpener.openActivity(GraphicsActivity.this, ConnectionActivity.class);
                break;
            case R.id.action_data:
                ActivityOpener.openActivity(GraphicsActivity.this, DataActivity.class);
                break;
            case R.id.action_settings:
                ActivityOpener.openActivity(GraphicsActivity.this, SettingsActivity.class);
                break;
            case R.id.action_help:
                ActivityOpener.openActivity(GraphicsActivity.this, HelpActivity.class);
                break;
        }
    }

    public void onClickButtonShowGraphic(View view) {
        graphicId = graphicSpinner.getSelectedItemPosition();
        locationName = locationSpiner.getSelectedItem().toString();

        Bundle bundle = new Bundle();
        bundle.putInt("graphicId", graphicId);
        bundle.putString("locationName", locationName);
        widgetChartTodayFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.graphics_activity_fragment_container, widgetChartTodayFragment);
        fragmentTransaction.commit();

    }
}
