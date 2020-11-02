package com.survivingwithandroid.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class LineChartDisplay extends Activity {
    LineChart lineChart;
    Vector v;
    SharedPreferences sharedpreferences;
    LineData lineData,lineData1;
    LineDataSet lineDataSet,lineDataSet1;
    ArrayList lineEntries,lineEntries1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechart);
        lineChart = findViewById(R.id.lineChart);

        sharedpreferences= getSharedPreferences("weather", Context.MODE_PRIVATE);
        String forecast=sharedpreferences.getString("forecast","");

        getEntries(forecast);
        lineDataSet = new LineDataSet(lineEntries, "");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);

       /* lineDataSet1 = new LineDataSet(lineEntries1, "");
        lineData1 = new LineData(lineDataSet1);
        lineChart.setData(lineData1);
        lineDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet1.setValueTextColor(Color.BLACK);
        lineDataSet1.setValueTextSize(18f);
*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    private void getEntries(String forecast) {
        DateFormat simple = new SimpleDateFormat("dd-MMM-yyyy");
        Date result = new Date(12);
        String sp[]=forecast.split(",");
        float m1=Float.parseFloat(sp[1]);
        float m2=Float.parseFloat(sp[4]);
        float m3=Float.parseFloat(sp[7]);
        float m4=Float.parseFloat(sp[10]);
        float m5=Float.parseFloat(sp[13]);
        float m6=Float.parseFloat(sp[16]);
        float m7=Float.parseFloat(sp[19]);

        float m11=Float.parseFloat(sp[2]);
        float m12=Float.parseFloat(sp[5]);
        float m13=Float.parseFloat(sp[8]);
        float m14=Float.parseFloat(sp[11]);
        float m15=Float.parseFloat(sp[14]);
        float m16=Float.parseFloat(sp[17]);
        float m17=Float.parseFloat(sp[20]);
        // Formatting Date according to the
        // given format
        float f=273.15f;
        System.out.println(simple.format(result));
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, m1-f));
        lineEntries.add(new Entry(2, m2-f));
        lineEntries.add(new Entry(3, m3-f));
        lineEntries.add(new Entry(4, m4-f));
        lineEntries.add(new Entry(5, m5-f));
        lineEntries.add(new Entry(6, m6-f));
        lineEntries.add(new Entry(7, m7-f));

        lineEntries1 = new ArrayList<>();
        lineEntries1.add(new Entry(1, m11-f));
        lineEntries1.add(new Entry(2, m12-f));
        lineEntries1.add(new Entry(3, m13-f));
        lineEntries1.add(new Entry(4, m14-f));
        lineEntries1.add(new Entry(5, m15-f));
        lineEntries1.add(new Entry(6, m16-f));
        lineEntries1.add(new Entry(7, m17-f));
    }
}