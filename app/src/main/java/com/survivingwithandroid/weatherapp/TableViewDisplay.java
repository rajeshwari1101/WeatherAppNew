package com.survivingwithandroid.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class TableViewDisplay extends Activity {
    private static  String[][] DATA_TO_SHOW ;
    String result1,result2,result3,result4,result5,result6,result7;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tableview);
        sharedpreferences= getSharedPreferences("weather", Context.MODE_PRIVATE);
        String forecast=sharedpreferences.getString("forecast","");
        getEntries(forecast);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
    private void getEntries(String forecast) {


        SimpleDateFormat  simple = new SimpleDateFormat("dd-MM-yyyy");
        String sp[]=forecast.split(",");

        try{

           // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
           // String dateInString = "01-01-1970 00:00:01";
            //Date date=java.util.Calendar.getInstance().getTime();
            System.out.println("Testing==step1==");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println("Testing==step2==");
            //Date after adding the days to the given date
            //String newDate = simple.format(c.getTime());
            //long time=date.getTime();
            //.out.println("Testing==time=="+time);
            result1 = simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            System.out.println("Testing==step3==");
            result2 =  simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            result3 =  simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            result4 = simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            result5 =  simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            result6 =  simple.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
            result7 =  simple.format(c.getTime());

        }catch(Exception e){
            System.out.println("Testing==error4=="+e.getMessage());
        }

        try{

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
        String val1[]={result1,""+(m1-f),""+(m11-f)};
        String val2[]={result2,""+(m2-f),""+(m12-f)};
        String val3[]={result3,""+(m3-f),""+(m13-f)};
        String val4[]={result4,""+(m4-f),""+(m14-f)};
        String val5[]={result5,""+(m5-f),""+(m15-f)};
        String val6[]={result6,""+(m6-f),""+(m16-f)};
        String val7[]={result7,""+(m7-f),""+(m17-f)};
        DATA_TO_SHOW=new String[7][];
        DATA_TO_SHOW[0]=val1;
        DATA_TO_SHOW[1]=val2;
        DATA_TO_SHOW[2]=val3;
        DATA_TO_SHOW[3]=val4;
        DATA_TO_SHOW[4]=val5;
        DATA_TO_SHOW[5]=val6;
        DATA_TO_SHOW[6]=val7;
        Context context=getApplicationContext();
            TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
            SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getApplicationContext(), "Date","Min.Temp.","Max.Temp.");
            simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            tableView.setHeaderAdapter(simpleTableHeaderAdapter);
            final int rowColorEven = ContextCompat.getColor(context, R.color.white);
            final int rowColorOdd = ContextCompat.getColor(context, R.color.view_background);
            tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
            //tableView.setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

            tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        }catch(Exception e){
            System.out.println("Testing==error5=="+e.getMessage());
        }
    }
}