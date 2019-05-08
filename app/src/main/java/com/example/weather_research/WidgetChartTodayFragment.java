package com.example.weather_research;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weather_research.date.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WidgetChartTodayFragment extends Fragment {

    private View view;
    private LineChart mChart;
    private String location;
    private ArrayList<String> locations = Constants.getLocations();
    private int graphicId;

    WeatherRepository weatherRepository = new WeatherRepository();
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_graph_chart, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            graphicId = bundle.getInt("graphicId");
            location = bundle.getString("locationName");
        }

        mChart = view.findViewById(R.id.fragment_graph_chart_line_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setBackgroundColor(Color.LTGRAY);

        switch (graphicId){

            case 0:
                if (location.equals("All")){
                    for (String location : locations){
                        ArrayList<Weather> weathers = findNeedWeathersFromTodayChart(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                        ArrayList<Float> temperatures = dataYAxis(weathers);
                        LineDataSet lineDataSet = lineDataSet(temperatures, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(weathers, false);
                    }
                }
                else {
                    ArrayList<Weather> weathers = findNeedWeathersFromTodayChart(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                    ArrayList<Float> temperatures = dataYAxis(weathers);
                    LineDataSet lineDataSet = lineDataSet(temperatures, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(weathers, false);
                }
                break;

            case 1:
                if (location.equals("All")){
                    for (String location : locations){
                        ArrayList<Weather> weathers = findNeedWeathersFromTodayChartSensiv(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                        ArrayList<Float> temperatures = dataYAxis(weathers);
                        LineDataSet lineDataSet = lineDataSet(temperatures, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(weathers, false);
                    }
                }
                else {
                    ArrayList<Weather> weathers = findNeedWeathersFromTodayChartSensiv(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                    ArrayList<Float> temperatures = dataYAxis(weathers);
                    LineDataSet lineDataSet = lineDataSet(temperatures, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(weathers, false);
                }
                break;

            case 2:
                if (location.equals("All")){
                    for (String location : locations){
                        ArrayList<Weather> weathers = findNeedWeathersFromTodayFull(DatabaseHelper.TABLE_ACCU_WEATHER, location);
                        ArrayList<Float> temperatures = dataYAxis(weathers);
                        LineDataSet lineDataSet = lineDataSet(temperatures, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(weathers, true);
                    }
                }
                else {
                    ArrayList<Weather> weathers = findNeedWeathersFromTodayFull(DatabaseHelper.TABLE_ACCU_WEATHER, location);
                    ArrayList<Float> temperatures = dataYAxis(weathers);
                    LineDataSet lineDataSet = lineDataSet(temperatures, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(weathers, true);
                }
                break;

            case 3:
                if (location.equals("All")){
                    for (String location : locations) {
                        ArrayList<Weather> realTemperatures;
                        ArrayList<Weather> nextTemperatures;
                        ArrayList<Float> difference;
                        realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                        nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 1);
                        difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                        LineDataSet lineDataSet = lineDataSet(difference, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(realTemperatures);
                    }
                }
                else {
                    ArrayList<Weather> realTemperatures;
                    ArrayList<Weather> nextTemperatures;
                    ArrayList<Float> difference;
                    realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                    nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 1);
                    difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                    LineDataSet lineDataSet = lineDataSet(difference, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(realTemperatures);
                }
                break;

            case 4:
                if (location.equals("All")){
                    for (String location : locations) {
                        ArrayList<Weather> realTemperatures;
                        ArrayList<Weather> nextTemperatures;
                        ArrayList<Float> difference;
                        realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                        nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 2);
                        difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                        LineDataSet lineDataSet = lineDataSet(difference, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(realTemperatures);
                    }
                }
                else {
                    ArrayList<Weather> realTemperatures;
                    ArrayList<Weather> nextTemperatures;
                    ArrayList<Float> difference;
                    realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                    nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 2);
                    difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                    LineDataSet lineDataSet = lineDataSet(difference, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(realTemperatures);
                }
                break;

            case 5:
                if (location.equals("All")){
                    for (String location : locations) {
                        ArrayList<Weather> realTemperatures;
                        ArrayList<Weather> nextTemperatures;
                        ArrayList<Float> difference;
                        realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                        nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 3);
                        difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                        LineDataSet lineDataSet = lineDataSet(difference, location);
                        dataSets.add(lineDataSet);

                        dataXAxis(realTemperatures);
                    }
                }
                else {
                    ArrayList<Weather> realTemperatures;
                    ArrayList<Weather> nextTemperatures;
                    ArrayList<Float> difference;
                    realTemperatures = findWeathersRealTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location);
                    nextTemperatures = findWeathersFromNextDayTemperature(DatabaseHelper.TABLE_OPEN_WEATHER, location, realTemperatures, 3);
                    difference = calculateDifferenceRealToNextTemperature(realTemperatures, nextTemperatures);
                    LineDataSet lineDataSet = lineDataSet(difference, location);
                    dataSets.add(lineDataSet);

                    dataXAxis(realTemperatures);
                }
                break;
        }

        LineData lineData = new LineData(dataSets);
        mChart.setData(lineData);

        return view;
    }

    public ArrayList<Weather> findNeedWeathersFromTodayChart(String tableName, String location){
        ArrayList<Weather> weathers;
        location = WeatherRepository.getLocationFromTownName(location, tableName);
        String effectiveDate = weatherRepository.findPreLastEffectiveDate(tableName, location);
        SimpleDateFormat searchFormat = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.ENGLISH);
        String dateToday = searchFormat.format(new Date()) ;
        ArrayList<Weather> needWeathers = new ArrayList<>();

        weathers = weatherRepository.findWeathers(tableName, location, effectiveDate);

        int oneMoreWeatherId = 0;
        int countWeather = 0;

        for (int i = 0; i < weathers.size(); i++){

            if (dateToday.equals(weathers.get(i).getDate().substring(0, 10))){
                needWeathers.add(weathers.get(i));
                countWeather++;
                if (countWeather == 8){
                    oneMoreWeatherId = i + 1;
                }
            }
        }
        if (oneMoreWeatherId != 0) {
            needWeathers.add(weathers.get(oneMoreWeatherId));
        }

        return needWeathers;
    }

    public ArrayList<Weather> findNeedWeathersFromTodayChartSensiv(String tableName, String location){
        ArrayList<Weather> weathers;
        location = WeatherRepository.getLocationFromTownName(location, tableName);
        String effectiveDate = weatherRepository.findLastEffectiveDate(tableName, location);
        SimpleDateFormat searchFormat = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.ENGLISH);
        String dateToday = searchFormat.format(new Date()) ;
        ArrayList<Weather> needWeathers = new ArrayList<>();

        weathers = weatherRepository.findWeathers(tableName, location, effectiveDate);

        int oneMoreWeatherId = 0;
        int countWeather = 0;

        for (int i = 0; i < weathers.size(); i++){

            if (dateToday.equals(weathers.get(i).getDate().substring(0, 10))){
                needWeathers.add(weathers.get(i));
                countWeather++;
                if (countWeather == 8){
                    oneMoreWeatherId = i + 1;
                }
            }
        }
        try {
            if (oneMoreWeatherId != 0) {
                needWeathers.add(weathers.get(oneMoreWeatherId));
                return needWeathers;
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(AppContext.getContext(),
                    "Данные не актуальны", Toast.LENGTH_SHORT);
            toast.show();
            return needWeathers;
        }

        return needWeathers;
    }

    public ArrayList<Weather> findNeedWeathersFromTodayFull(String tableName, String location){
        ArrayList<Weather> weathers;
        location = WeatherRepository.getLocationFromTownName(location, tableName);
        String effectiveDate = weatherRepository.findLastEffectiveDate(tableName, location);
        SimpleDateFormat searchFormat = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.ENGLISH);
        String dateToday = searchFormat.format(new Date()) ;
        ArrayList<Weather> needWeathers = new ArrayList<>();

        weathers = weatherRepository.findWeathers(tableName, location, effectiveDate);

        return weathers;
    }

    public ArrayList<Weather> findWeathersRealTemperature(String tableName, String location) {
        ArrayList<Weather> weathers;
        location = WeatherRepository.getLocationFromTownName(location, tableName);
        weathers = weatherRepository.findWeathers(tableName, location);

        String defaultTime = "15:00";
        if (tableName.equals(DatabaseHelper.TABLE_ACCU_WEATHER)){
            defaultTime = "07:00";
        }

        ArrayList<Weather> realTemperatureWeathers = new ArrayList<>();


        for (Weather weather : weathers){
            if (weather.getEffectiveDate().substring(0, 10).equals(weather.getDate().substring(0, 10))
                    & weather.getDate().substring(11).equals(defaultTime)){
                realTemperatureWeathers.add(weather);
            }
        }


        return realTemperatureWeathers;
    }

    public ArrayList<Weather> findWeathersFromNextDayTemperature(String tableName, String location, ArrayList<Weather> realTemperatureWeathers, int howMarchDays){
        ArrayList<Weather> nextDayWeathers = new ArrayList<>();
        location = WeatherRepository.getLocationFromTownName(location, tableName);

        DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        try {
            for (Weather weather : realTemperatureWeathers){
                try {
                Date date = parseFormat.parse(weather.getDate());
                Calendar plusDay = Calendar.getInstance();
                plusDay.setTime(date);
                plusDay.add(Calendar.DATE, howMarchDays);
                date = plusDay.getTime();
                String needDate = parseFormat.format(date);

                Weather needWeather = weatherRepository.findWeatherByDate(tableName, location, weather.getEffectiveDate(), needDate );
                nextDayWeathers.add(needWeather);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return nextDayWeathers;
    }

    public ArrayList<Float> calculateDifferenceRealToNextTemperature(ArrayList<Weather> realTemperatures, ArrayList<Weather> nextTemperatures){
        int countOfIterations = Math.max(realTemperatures.size(), nextTemperatures.size());
        ArrayList<Float> differences = new ArrayList<>();

        for (int i = 0; i < countOfIterations; i++){
            try {
                float difference = realTemperatures.get(i).getTemperature() - nextTemperatures.get(i).getTemperature();
                differences.add(difference);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return  differences;
    }

    public ArrayList<Float> dataYAxis(ArrayList<Weather> weathers){
        ArrayList<Float> temperatures = new ArrayList<>();

        for (Weather weather : weathers){
            temperatures.add(weather.getTemperature());
        }

        return temperatures;
    }

    public LineDataSet lineDataSet(ArrayList<Float> temperatures, String location){

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 1; i <= temperatures.size(); i++) {
            values.add(new Entry(i, temperatures.get(i -1)));
        }

        LineDataSet lineData = new LineDataSet(values, location);

        lineData.setFillAlpha(110);

        switch (location){
            case "Харьков":
                lineData.setColor(Color.RED);
                break;
            case "Москва":
                lineData.setColor(Color.BLUE);
                break;
            case "Вильнюс":
                lineData.setColor(Color.GREEN);
                break;
            case "Люблин":
                lineData.setColor(Color.YELLOW);
                break;
        }
        lineData.setLineWidth(3f);
        lineData.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineData.setValueTextSize(12f);
        lineData.setValueTextColor(Color.GRAY);
        lineData.setCircleColor(Color.GRAY);
        lineData.setCircleRadius(4f);

        return lineData;
    }

    public void dataXAxis(ArrayList<Weather> weathers, boolean aLotDays){
        final ArrayList<String> dates = new ArrayList<>();
        int substring = 11;

        if (aLotDays) {
            substring = 8;
        }

        for (Weather weather : weathers){
            dates.add(weather.getDate().substring(substring));
        }

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return   dates.get((int) value - 1);
            }

        });
    }

    public void dataXAxis(ArrayList<Weather> weathers){
        final ArrayList<String> dates = new ArrayList<>();

        for (Weather weather : weathers){
            dates.add(weather.getDate().substring(5, 10));
        }

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return   dates.get((int) value - 1);
            }

        });
    }

}
