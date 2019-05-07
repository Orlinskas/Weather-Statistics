package com.example.weather_research;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather_research.date.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WidgetChartTodayFragment extends Fragment {

    private View view;
    private LineChart mChart;
    private String location;
    private ArrayList<String> locations = Constants.getLocations();
    private int graphicId;

    WeatherRepository weatherRepository = new WeatherRepository();

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

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


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

    public ArrayList<Float> dataYAxis (ArrayList<Weather> weathers){
        ArrayList<Float> temperatures = new ArrayList<>();

        for (Weather weather : weathers){
            temperatures.add(weather.getTemperature());
        }

        return temperatures;
    }

    public LineDataSet lineDataSet (ArrayList<Float> temperatures, String location){

        ArrayList<Entry> openWeatherValues = new ArrayList<>();

        for (int i = 1; i <= temperatures.size(); i++) {
            openWeatherValues.add(new Entry(i, temperatures.get(i -1)));
        }

        LineDataSet lineData = new LineDataSet(openWeatherValues, location);

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
            //temperatures.add(weather.getTemperature());
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
                return   dates.get((int) value - 1);  // xVal is a string array xVal[(int) value-1];
            }

        });
    }

}
