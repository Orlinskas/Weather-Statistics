package com.example.weather_research;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weather_research.date.DatabaseAdapter;
import com.example.weather_research.date.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ConnectionActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button buttonConnection, buttonDeleter;
    private TextView tvTodayDate, tvLastDataDate, tvDataMessage;
    private LinearLayout linearLayoutConsole;
    private ScrollView scrollView;

    private SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private String todayDate = todayFormat.format(new Date());

    private DatabaseAdapter database;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        progressBar = findViewById(R.id.activity_connection_pb);
        tvTodayDate = findViewById(R.id.activity_connection_tv_today_date);
        tvLastDataDate = findViewById(R.id.activity_connection_tv_last_data_date);
        tvDataMessage = findViewById(R.id.activity_connection_tv_data_message);
        buttonConnection = findViewById(R.id.activity_connection_btn_get);
        buttonDeleter = findViewById(R.id.activity_connection_btn_delete_all_from_last_effectiveDate);
        linearLayoutConsole = findViewById(R.id.activity_connection_ll_console);
        scrollView = findViewById(R.id.activity_connection_sv_console);

        progressBar.setVisibility(View.INVISIBLE);
        tvTodayDate.setText(todayDate);

        database = new DatabaseAdapter(this);

        tvLastDataDate.setText(searchLastEffectiveDateInAllTables(Constants.TABLENAMES));

        buttonConnection.setClickable(checkNeedDataUpdate());

        SharedPrefData.setSharedPrefSettingsAndData(getSharedPreferences(SharedPrefData.SETTINGS_AND_DATA, MODE_PRIVATE));

        if(!SharedPrefData.getFirstRun()){
            firstRun();
        }

        SharedPrefData.setTrueFirstRun();

        showLastErrorInGlobalConsole();

        AppContext.setContext(this);

    }

    public String searchLastEffectiveDateInAllTables(String[] tableName){
        String [] effectiveDates = new String[tableName.length];
        DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat returnFormat = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_00);
        ArrayList <Date>  dates = new ArrayList<>();
        Date lastBiggerDate;

        try {
            for (int i = 0; i < tableName.length; i++){ //получаем массив последних дат из всех таблиц
                try {
                    database.open();
                    ArrayList <Integer> weathersIds = database.getWeathersIds(tableName[i]);
                    int lastId = Collections.max(weathersIds);
                    Weather lastWeather = database.getWeather(lastId, tableName[i]);
                    database.close();
                    effectiveDates[i] = lastWeather.getEffectiveDate();
                }
                catch (Exception e){
                    effectiveDates[i] = "1996-01-22 14:00";
                }
            }

            for (String date : effectiveDates){ //делаем их датами что бы сравнить
                dates.add(parseFormat.parse(date));
            }

            long max = dates.get(0).getTime();

            for (Date date : dates){ //сравниваем и получаем самую большую дату в мс
                if (date.getTime() > max){
                    max = date.getTime();
                }
            }

            lastBiggerDate = new Date(max);

        }
        catch (Exception e){
            return Constants.ERROR_DATA_EMPTY;
        }

        return returnFormat.format(lastBiggerDate);
    }

    public boolean checkNeedDataUpdate(){
        String lastDataUpdate = searchLastEffectiveDateInAllTables(Constants.TABLENAMES);
        StringBuilder stringBuffer = new StringBuilder(lastDataUpdate);
        lastDataUpdate = stringBuffer.delete(10, lastDataUpdate.length()).toString();
        String todayDate = tvTodayDate.getText().toString();

        if (todayDate.equals(lastDataUpdate)) {
            tvDataMessage.setText(getString(R.string.dont_need_data_update_do_tomorow));
            return false;
        } else {
            tvDataMessage.setText(getString(R.string.need_data_update));
            return true;
        }
    }

    public void firstRun() {
        //приложение имеет таблицу данных со всеми ошибками, установим при первом запуске первую
        //ошибку в виде приветствия
        database.open();
        database.insertLastErrorText("Welcome to app, error stack empty\n", getClass());
        database.close();

    }

    public void onClickConnection(View view) {
        addTextViewInConsole("click button");
        new ApiGetDataInsertAccuWeather().execute(Constants.ACCUWEATHER_KHARKIV_ID,
                Constants.ACCUWEATHER_LUBLIN_ID, Constants.ACCUWEATHER_MOSKOW_ID,
                Constants.ACCUWEATHER_VILNIUS_ID);
    }

    public void showLastErrorInGlobalConsole() {
        GlobalConsoleFragment consoleFragment = (GlobalConsoleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_connection_fragment);

        try {
            database.open();
            long id = database.getCount(DatabaseHelper.TABLE_ERROR_MESSAGES);
            if (consoleFragment != null) {
                consoleFragment.setTvLastErrorText(database.getErrorText(id));
            }
            else {
                database.insertLastErrorText(Constants.ERROR_DATA, getClass());
            }
            database.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            database.insertLastErrorText(Constants.ERROR_DATA, getClass());
        }
    }

    public void addTextViewInConsole(String addedText) {
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

    public void onClickDeleteAllFromLastEffectiveDate(View view) {
        showDialog(Constants.IDD_DELETE);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constants.IDD_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Вы уверены, что хотите удалить последние полученные данные? ")
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new DeleteAllDataWithEffectiveDate()
                                                .execute(searchLastEffectiveDateInAllTables(Constants.TABLENAMES));
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();
            default:
                return null;
        }
    }

    class DeleteAllDataWithEffectiveDate extends AsyncTask<String, String, Void> {
        ArrayList<Integer> weathersIds = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonDeleter.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... effectiveDate) {
            try {
                publishProgress("start delete data(AccuWeather)");
                database.open();
                weathersIds = database.getWeathersIds(effectiveDate[0], DatabaseHelper.TABLE_ACCU_WEATHER);
                database.close();

                for (Integer countID : weathersIds) {
                   database.open();
                   database.delete(countID, DatabaseHelper.TABLE_ACCU_WEATHER);
                   database.close();

                   publishProgress("delete - " + countID.toString() + " id element");
                }
                publishProgress("data delete completed(AccuWeather)");
                publishProgress("deleted " + weathersIds.size() + " elements (AccuWeather)");
            } catch (Exception e) {
                database.open();
                database.insertLastErrorText(Constants.ERROR_THREAD, getClass());
                database.close();
            }

            //Начинается удаление из другой таблицы

            try {
                publishProgress("start delete data(OpenWeather)");
                database.open();
                weathersIds = database.getWeathersIds(effectiveDate[0], DatabaseHelper.TABLE_OPEN_WEATHER);
                database.close();

                for (Integer countID : weathersIds) {
                    database.open();
                    database.delete(countID, DatabaseHelper.TABLE_OPEN_WEATHER);
                    database.close();

                    publishProgress("delete - " + countID.toString() + " id element");
                }
                publishProgress("data delete completed(OpenWeather)");
                publishProgress("deleted " + weathersIds.size() + " elements (OpenWeather)");
            } catch (Exception e) {
                database.open();
                database.insertLastErrorText(Constants.ERROR_THREAD, getClass());
                database.close();
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
            addTextViewInConsole("all data delete completed");
            buttonDeleter.setClickable(true);
            tvLastDataDate.setText(searchLastEffectiveDateInAllTables(Constants.TABLENAMES));
            buttonConnection.setClickable(checkNeedDataUpdate());
        }
    }

    class ApiGetDataInsertAccuWeather extends AsyncTask<String, String, Void> {
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderAccuWeather requestSenderAccuWeather = new WeatherRequestSenderAccuWeather();
        WeatherParserAcuuWeather parserAcuuWeather = new WeatherParserAcuuWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            buttonConnection.setClickable(false);
        }

        @Override
        protected Void doInBackground(String... locationID) {
            try {
                publishProgress("start collect data(AccuWeather)");

                for (String aLocationID : locationID) {
                    weathers = parserAcuuWeather
                            .parse(requestSenderAccuWeather.requestWeather(aLocationID));

                    publishProgress("parse location - " + Constants.getTownFromId(aLocationID));

                    for (Weather weather : weathers) {
                        database.open();
                        database.insert(weather, DatabaseHelper.TABLE_ACCU_WEATHER);
                        database.close();

                        publishProgress("write in data base id- " + weathers.indexOf(weather));
                    }
                }
            } catch (Exception e) {
                database.open();
                database.insertLastErrorText(Constants.ERROR_THREAD, getClass());
                database.close();
                new ApiGetDataInsertOpenWeather().execute(Constants.OPENWEATHERMAP_KHARKIV_ID,
                        Constants.OPENWEATHERMAP_MOSKOW_ID, Constants.OPENWEATHERMAP_LUBLIN_ID,
                        Constants.OPENWEATHERMAP_VILNIUS_ID);
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
            buttonConnection.setClickable(checkNeedDataUpdate());
        }

    }

    class ApiGetDataInsertOpenWeather extends AsyncTask<String, String, Void> {
        ArrayList<Weather> weathers = new ArrayList<>();
        WeatherRequestSenderOpenWeather requestSenderOpenWeather = new WeatherRequestSenderOpenWeather();
        WeatherParserOpenWeather parserOpenWeather = new WeatherParserOpenWeather();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonConnection.setClickable(false);
        }

        @Override
        protected Void doInBackground(String... locationID) {
            try {
                publishProgress("start collect data(OpenWeather)");

                for (String aLocationID : locationID) {
                    weathers = parserOpenWeather
                            .parse(requestSenderOpenWeather.requestWeather(aLocationID));

                    publishProgress("parse location - " + Constants.getTownFromId(aLocationID));

                    for (Weather weather : weathers) {
                        database.open();
                        database.insert(weather, DatabaseHelper.TABLE_OPEN_WEATHER);
                        database.close();

                        publishProgress("write in data base id- " + weathers.indexOf(weather));
                    }
                }
            } catch (Exception e) {
                 database.open();
                 database.insertLastErrorText(Constants.ERROR_THREAD, getClass());
                 database.close();
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
            tvLastDataDate.setText(searchLastEffectiveDateInAllTables(Constants.TABLENAMES));
            buttonConnection.setClickable(checkNeedDataUpdate());
        }
    }

}
