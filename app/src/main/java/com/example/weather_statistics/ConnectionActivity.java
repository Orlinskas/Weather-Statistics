package com.example.weather_statistics;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weather_statistics.date.DatabaseAdapter;
import com.example.weather_statistics.date.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ConnectionActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button buttonConnection;
    private TextView tvTodayDate, tvLastDataDate, tvDataMessage;
    private LinearLayout linearLayoutConsole;
    private ScrollView scrollView;


    SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    String todayDate = todayFormat.format(new Date());

    DatabaseAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        progressBar = findViewById(R.id.activity_connection_pb);
        tvTodayDate = findViewById(R.id.activity_connection_tv_today_date);
        tvLastDataDate = findViewById(R.id.activity_connection_tv_last_data_date);
        tvDataMessage = findViewById(R.id.activity_connection_tv_data_message);
        buttonConnection =  findViewById(R.id.activity_connection_btn_get);
        linearLayoutConsole = findViewById(R.id.activity_connection_ll_console);
        scrollView = findViewById(R.id.activity_connection_sv_console);

        progressBar.setVisibility(View.INVISIBLE);
        tvTodayDate.setText(todayDate);

        SharedPrefData.setSharedPrefSettingsAndData(getSharedPreferences(SharedPrefData.SETTINGS_AND_DATA, MODE_PRIVATE));
        tvLastDataDate.setText(SharedPrefData.getLastDataDate());

        database = new DatabaseAdapter(this);

        buttonConnection.setClickable(checkNeedDataUpdate());
    }

    class ApiGetDataInsertAccuWeather extends AsyncTask <String, String, Void> {
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
        WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... locationID) {
            publishProgress("start collect data(AccuWeather)");
            for (int countID = 0; countID < locationID.length; countID++){
                weathers = parserAcuuWeather
                        .parse(requestSenderAccuWeather.requestWeather(locationID[countID]));

                publishProgress("parse location - " + Constants.getTownFromId(locationID[countID]));

                for (Weather weather : weathers ){
                    database.open();
                    database.insert(weather, DatabaseHelper.TABLE_ACCU_WEATHER);
                    database.close();

                    publishProgress("write in data base id- " + weathers.indexOf(weather));
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            addTextViewInConsole(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            addTextViewInConsole("data collect completed(AccuWeather)");
            addTextViewInConsole("call another AsyncTask(OpenWeather)");
            new ApiGetDataInsertOpenWeather().execute(Constants.OPENWEATHERMAP_KHARKIV_ID,
                    Constants.OPENWEATHERMAP_MOSKOW_ID, Constants.OPENWEATHERMAP_LUBLIN_ID,
                    Constants.OPENWEATHERMAP_VILNIUS_ID);
        }

    }

    class ApiGetDataInsertOpenWeather extends AsyncTask <String, String, Void>{
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();
        WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... locationID) {
            publishProgress("start collect data(OpenWeather)");
            int countID;

            for (countID = 0; countID < locationID.length; countID++){
                weathers = parserOpenWeather
                        .parse(requestSenderOpenWeather.requestWeather(locationID[countID]));

                publishProgress("parse location - " + Constants.getTownFromId(locationID[countID]));

                for (Weather weather : weathers ){
                    database.open();
                    database.insert(weather, DatabaseHelper.TABLE_OPEN_WEATHER);
                    database.close();

                    publishProgress("write in data base id- " + weathers.indexOf(weather));
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            addTextViewInConsole(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            addTextViewInConsole("data collect completed(OpenWeather)");
            addTextViewInConsole("all data collection completed");
            setLastDataDate();
            buttonConnection.setClickable(checkNeedDataUpdate());
        }
    }

    public void onClickConnection(View view) {
        addTextViewInConsole("click button");
        new ApiGetDataInsertAccuWeather().execute(Constants.ACCUWEATHER_KHARKIV_ID,
                Constants.ACCUWEATHER_VILNIUS_ID, Constants.ACCUWEATHER_MOSKOW_ID,
                Constants.ACCUWEATHER_LUBLIN_ID);
    }

    public void addTextViewInConsole(String addedText){
        SimpleDateFormat consoleFormat = new SimpleDateFormat("mm:ss:SSS", Locale.ENGLISH);
        String consoleDate = consoleFormat.format(new Date());
        int lengthAddedText = addedText.length();
        int needCountSpace = 50 - lengthAddedText;
        char[] charArray = new char[needCountSpace];
        Arrays.fill(charArray, ' ');
        String space = new String(charArray);
        String consoleText = addedText + space + consoleDate;

        final TextView consoleTextView = new TextView(this);
        consoleTextView.setTextColor(Color.GRAY);
        consoleTextView.setText(consoleText);
        linearLayoutConsole.addView(consoleTextView);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public boolean checkNeedDataUpdate(){
        if(!tvLastDataDate.getText().equals(tvTodayDate.getText())){
            tvDataMessage.setText("Нужно обновить данные, в 12-13 часов");
            return true;
        }
        else{
            tvDataMessage.setText("Данные соответствуют, обновите завтра");
            return false;
        }
    }

    public void setLastDataDate(){

        if(checkNeedDataUpdate()){
            database.open();
            long countAccuWeather = database.getCount(DatabaseHelper.TABLE_ACCU_WEATHER);
            database.close();
            database.open();
            long countOpenWeather = database.getCount(DatabaseHelper.TABLE_OPEN_WEATHER);
            database.close();
            database.open();
            Weather lastAccuWeather = database.getWeather(countAccuWeather, DatabaseHelper.TABLE_ACCU_WEATHER);
            database.close();
            database.open();
            Weather lastOpenWeather = database.getWeather(countOpenWeather, DatabaseHelper.TABLE_OPEN_WEATHER);
            database.close();
            if (lastAccuWeather.getEffectiveDate().equals(lastOpenWeather.getEffectiveDate())){
                String effectiveDate = lastAccuWeather.getEffectiveDate();
                StringBuilder stringBuffer = new StringBuilder(effectiveDate);
                effectiveDate = stringBuffer.delete(10, lastAccuWeather.getEffectiveDate().length()).toString();
                SharedPrefData.setLastDataDate(effectiveDate);
                tvLastDataDate.setText(SharedPrefData.getLastDataDate());
            }
            else{
                tvDataMessage.setText("error in data");
            }
        }

    }
}
