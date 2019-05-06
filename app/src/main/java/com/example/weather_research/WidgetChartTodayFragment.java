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
    private String apiName, location, needEffectiveDate;
    private int graphicId;

    WeatherRepository weatherRepository = new WeatherRepository();
    ArrayList<Weather> weathers = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<Float> temperatures = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_graph_chart, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            graphicId = bundle.getInt("graphicId");
            location = bundle.getString("locationName");
        }

        switch (graphicId){
            case 0:
                getDataFromChartToday();
                break;
        }

        mChart = view.findViewById(R.id.fragment_graph_chart_line_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setBackgroundColor(Color.LTGRAY);

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

        ArrayList<Entry> openWeatherValues = new ArrayList<>();

        for (int i = 1; i <= temperatures.size(); i++) {
            openWeatherValues.add(new Entry(i, temperatures.get(i -1)));
        }

        LineDataSet openWeatherLine = new LineDataSet(openWeatherValues, location);

        openWeatherLine.setFillAlpha(110);
        openWeatherLine.setColor(Color.RED);
        openWeatherLine.setLineWidth(3f);
        openWeatherLine.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        openWeatherLine.setValueTextSize(12f);
        openWeatherLine.setValueTextColor(Color.GRAY);
        openWeatherLine.setCircleColor(Color.GRAY);
        openWeatherLine.setCircleRadius(4f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(openWeatherLine);

        LineData data = new LineData((dataSets));

        mChart.setData(data);

        return view;
    }

    public void getDataFromChartToday(){
        String tableName = DatabaseHelper.TABLE_OPEN_WEATHER;
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

        for (Weather weather : needWeathers){
            dates.add(weather.getDate().substring(11));
            temperatures.add(weather.getTemperature());
        }
    }
}
