package com.example.weather_research;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weather_research.date.DatabaseAdapter;
import com.example.weather_research.date.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

        SharedPrefData.setSharedPrefSettingsAndData(getSharedPreferences(SharedPrefData.SETTINGS_AND_DATA, MODE_PRIVATE));
        tvLastDataDate.setText(SharedPrefData.getLastDataDate());

        database = new DatabaseAdapter(this);

        buttonConnection.setClickable(checkNeedDataUpdate());

        setMessageInGlobalConsole();

        AppContext.setContext(this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constants.IDD_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Вы уверены, что хотите удалить данные за - " + getLastDataUpdateWithDataBase())
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new DeleteAllDataWithEffectiveDate()
                                                .execute(getLastDataUpdateWithDataBase());
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

    public void onClickConnection(View view) {
        addTextViewInConsole("click button");
        new ApiGetDataInsertAccuWeather().execute(Constants.ACCUWEATHER_KHARKIV_ID,
                Constants.ACCUWEATHER_LUBLIN_ID, Constants.ACCUWEATHER_MOSKOW_ID,
                Constants.ACCUWEATHER_VILNIUS_ID);

        SharedPrefData.setTrueFirstRun();
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

    public boolean checkNeedDataUpdate() {
        if (!tvLastDataDate.getText().equals(tvTodayDate.getText())) {
            tvDataMessage.setText(getString(R.string.need_data_update));
            return true;
        } else {
            tvDataMessage.setText(getString(R.string.dont_need_data_update_do_tomorow));
            return false;
        }
    }

    public void setLastDataDate() {

        if (checkNeedDataUpdate()) {
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
            if (lastAccuWeather.getEffectiveDate().equals(lastOpenWeather.getEffectiveDate())) {
                String effectiveDate = lastAccuWeather.getEffectiveDate();
                StringBuilder stringBuffer = new StringBuilder(effectiveDate);
                effectiveDate = stringBuffer.delete(10, lastAccuWeather.getEffectiveDate().length()).toString();
                SharedPrefData.setLastDataDate(effectiveDate);
                tvLastDataDate.setText(SharedPrefData.getLastDataDate());
            } else {
                tvDataMessage.setText(Constants.ERROR_DATA);
                database.open();
                database.insertLastErrorText(Constants.ERROR_DATA, getClass());
                database.close();
            }
        }

    }

    public String getLastDataUpdateWithDataBase(){
        String lastDataUpdate;
        ArrayList<Weather> weathers = new ArrayList<>();
        database.open();
        if (database.getCount(DatabaseHelper.TABLE_OPEN_WEATHER) == 0 | database.getCount(DatabaseHelper.TABLE_ACCU_WEATHER) == 0){
            return Constants.MESSAGE_SOME_DATA_EMPTY;
        }
        weathers = database.getWeathers(DatabaseHelper.TABLE_OPEN_WEATHER);
        database.close();
        lastDataUpdate = weathers.get(weathers.size() - 1).getEffectiveDate();

        if (lastDataUpdate != null) {
            return lastDataUpdate;
        }
        else {
            return Constants.ERROR_DATA;
        }
    }

    private void setMessageInGlobalConsole() {
        GlobalConsoleFragment consoleFragment = (GlobalConsoleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_connection_fragment);

        if (SharedPrefData.getFirstRun()) {
            database.open();
            long id = database.getCount(DatabaseHelper.TABLE_ERROR_MESSAGES);
            if (consoleFragment != null) {
                consoleFragment.setTvLastErrorText(database.getErrorText(id));
            } else database.insertLastErrorText(Constants.ERROR_ACTIVITY, getClass());
            database.close();
        } else {
            if (consoleFragment != null) {
                consoleFragment.setTvLastErrorText("Welcome to app, error stack empty");
            } else database.insertLastErrorText(Constants.ERROR_ACTIVITY, getClass());
        }
    }

    public void onClickDeleteAllFromLastEffectiveDate(View view) {
        showDialog(Constants.IDD_DELETE);
    }

    class DeleteAllDataWithEffectiveDate extends AsyncTask<String, String, Void> {
        ArrayList<Integer> weathersId = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonDeleter.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... effectiveDate) {
            publishProgress("start delete data(AccuWeather)");
            database.open();
            weathersId = database.getWeathersIdWithEffectiveDate(effectiveDate[0], DatabaseHelper.TABLE_ACCU_WEATHER);
            database.close();

            for (Integer countID : weathersId) {
               database.open();
               database.delete(countID, DatabaseHelper.TABLE_ACCU_WEATHER);
               database.close();

               publishProgress("delete - " + countID.toString() + " id element");
            }
            publishProgress("data delete completed(AccuWeather)");
            publishProgress("deleted " + weathersId.size() + " elements (AccuWeather)");

            //Начинается удаление из другой таблицы
            publishProgress("start delete data(OpenWeather)");
            database.open();
            weathersId = database.getWeathersIdWithEffectiveDate(effectiveDate[0], DatabaseHelper.TABLE_OPEN_WEATHER);
            database.close();

            for (Integer countID : weathersId) {
                database.open();
                database.delete(countID, DatabaseHelper.TABLE_OPEN_WEATHER);
                database.close();

                publishProgress("delete - " + countID.toString() + " id element");
            }
            publishProgress("data delete completed(OpenWeather)");
            publishProgress("deleted " + weathersId.size() + " elements (OpenWeather)");
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

            SharedPrefData.setLastDataDate("need reGet");
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
        }

        @Override
        protected Void doInBackground(String... locationID) {
            publishProgress("start collect data(AccuWeather)");
            for (int countID = 0; countID < locationID.length; countID++) {
                weathers = parserAcuuWeather
                        .parse(requestSenderAccuWeather.requestWeather(locationID[countID]));

                publishProgress("parse location - " + Constants.getTownFromId(locationID[countID]));

                for (Weather weather : weathers) {
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

    class ApiGetDataInsertOpenWeather extends AsyncTask<String, String, Void> {
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

            for (int countID = 0; countID < locationID.length; countID++) {
                weathers = parserOpenWeather
                        .parse(requestSenderOpenWeather.requestWeather(locationID[countID]));

                publishProgress("parse location - " + Constants.getTownFromId(locationID[countID]));

                for (Weather weather : weathers) {
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

}
