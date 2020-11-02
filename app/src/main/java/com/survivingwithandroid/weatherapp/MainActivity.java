package com.survivingwithandroid.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;

import com.survivingwithandroid.weatherapp.model.Weather;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class MainActivity extends Activity {

	CardView card;
	private TextView cityText;
	private TextView condDescr;
	private TextView temp,presslab,humLab,windLab;
	private TextView press;
	Vector v;
	private TextView windSpeed,sunrise,sunset;
	private TextView windDeg,latitude;
	ToggleButton toggle;
	String forecastresult;
	String result;
	private TextView hum,min,max;
	private ImageView imgView;
	EditText scity;
	Button search,graph,table;
	String city;
	SharedPreferences sharedpreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		city = "Pune";
		toggle=findViewById(R.id.toggleButton);
		card=findViewById(R.id.card4);
		cityText = (TextView) findViewById(R.id.cityText);
		min= (TextView) findViewById(R.id.min);
		sunrise= (TextView) findViewById(R.id.sunrise);
		sunset= (TextView) findViewById(R.id.sunset);
		max= (TextView) findViewById(R.id.max);
		sharedpreferences= getSharedPreferences("weather", Context.MODE_PRIVATE);
		condDescr = (TextView) findViewById(R.id.condDescr);
		temp = (TextView) findViewById(R.id.temp);
		latitude = (TextView) findViewById(R.id.latitude);
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		presslab = (TextView) findViewById(R.id.pressLab);
		humLab = (TextView) findViewById(R.id.humLab);
		windLab= (TextView) findViewById(R.id.windLab);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		search=   findViewById(R.id.search);
		graph=   findViewById(R.id.graph);
		table=   findViewById(R.id.table);
		scity=   findViewById(R.id.city);
		imgView = (ImageView) findViewById(R.id.condIcon);
		GeoCodingLocation locationAddress = new GeoCodingLocation();
		result=locationAddress.getAddressFromLocation(city,
				getApplicationContext(), new GeocoderHandler());
		search.setOnClickListener(new View.OnClickListener() {
		 	@Override
				public void onClick(View v) {
					if(isEmpty(scity)){
						showAlert("Please Enter City Name");
					}else{
						city=scity.getText().toString();

						GeoCodingLocation locationAddress = new GeoCodingLocation();
						result=locationAddress.getAddressFromLocation(city,
								getApplicationContext(), new GeocoderHandler());
						JSONWeatherTask task = new JSONWeatherTask();
						latitude.setText(result);
						task.execute(new String[]{city});
					}
				}
		});

		graph.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GeoCodingLocation locationAddress = new GeoCodingLocation();
				result=locationAddress.getAddressFromLocation(city,
						getApplicationContext(), new GeocoderHandler());
				ForecastTask task = new ForecastTask(result,"graph");
				task.execute(new String[]{city});
			}
		});
		table.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GeoCodingLocation locationAddress = new GeoCodingLocation();
				result=locationAddress.getAddressFromLocation(city,
						getApplicationContext(), new GeocoderHandler());
				ForecastTask task = new ForecastTask(result,"table");
				task.execute(new String[]{city});
			}
		});

		toggle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String text=toggle.getText().toString();
				if(text.equalsIgnoreCase("Hide Details")){
					press.setVisibility(View.VISIBLE);
					windSpeed.setVisibility(View.VISIBLE);
					windDeg.setVisibility(View.VISIBLE);
					presslab.setVisibility(View.VISIBLE);
					humLab.setVisibility(View.VISIBLE);
					windLab.setVisibility(View.VISIBLE);
					sunrise.setVisibility(View.VISIBLE);
					sunset.setVisibility(View.VISIBLE);
					card.setVisibility(View.VISIBLE);
				}else{
					card.setVisibility(View.GONE);
					press.setVisibility(View.GONE);
					windSpeed.setVisibility(View.GONE);
					windDeg.setVisibility(View.GONE);
					presslab.setVisibility(View.GONE);
					humLab.setVisibility(View.GONE);
					windLab.setVisibility(View.GONE);
					sunrise.setVisibility(View.GONE);
					sunset.setVisibility(View.GONE);
				}
			}
		});
		JSONWeatherTask task = new JSONWeatherTask();
		task.execute(new String[]{city});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
		
		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

			try {
				weather = JSONWeatherParser.getWeather(data);
				
				// Let's retrieve the icon
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
				
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return weather;
		
	}

	@Override
		protected void onPostExecute(Weather weather) {			
			super.onPostExecute(weather);
			
			if (weather.iconData != null && weather.iconData.length > 0) {
				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length); 
				imgView.setImageBitmap(img);
			}
			DateFormat simple = new SimpleDateFormat("HH:mm:ss:SSS Z");
			Date result = new Date(weather.location.getSunrise());
			Date result1 = new Date(weather.location.getSunset());
			sunrise.setText("Sunrise:- "+simple.format(result));
			sunset.setText("Sunset:- "+simple.format(result1));
			cityText.setText("Location:- "+weather.location.getCity() + "," + weather.location.getCountry());
			condDescr.setText("Weather:- "+weather.currentCondition.getDescr() + "(" + weather.currentCondition.getDescr() + ")");
			temp.setText("Temperature:- " + Math.round((weather.temperature.getTemp() - 273.15)) + " C");
			min.setText("Min. Temp.:- " + Math.round((weather.temperature.getMinTemp() - 273.15)) + " C");
			max.setText("Max. Temp. :- " + Math.round((weather.temperature.getMaxTemp() - 273.15)) + " C");
			hum.setText(weather.currentCondition.getHumidity() + "%");
			press.setText( weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText( weather.wind.getSpeed() + " mps");
			//windDeg.setText("Wind Speed:-" + weather.wind.getDeg() + "");
		}
}
	private class ForecastTask extends AsyncTask<String, Void, Weather> {
		public ForecastTask(){

		}
		String result,type;
		public ForecastTask(String result,String type){
			this.result=result;
			this.type=type;
		}

		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();

			System.out.println("Testing==result=="+result);
			String data = ( (new WeatherHttpClient()).getForecastWeatherData(result));
			System.out.println("Testing==data=="+result);
			try {
				//weather = JSONWeatherParser.getWeatherForecast(data);
				String forecast  = JSONWeatherParser.getWeatherForecast(data);
				System.out.println("Testing==forecast=="+forecast);
				SharedPreferences.Editor editor=sharedpreferences.edit();
				editor.putString("forecast",forecast);
				editor.commit();
				// Let's retrieve the icon
				//weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

			} catch (JSONException e) {
				System.out.println("Testing==error=="+e.getMessage());
				e.printStackTrace();
			}
			return weather;

		}

		@Override
		protected void onPostExecute(Weather weather) {
			super.onPostExecute(weather);
			try {
				/*if (weather.iconData != null && weather.iconData.length > 0) {
					Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
					imgView.setImageBitmap(img);
				}*/
				if(type.equalsIgnoreCase("graph")) {
					Intent i = new Intent(getApplicationContext(), LineChartDisplay.class);
					startActivity(i);
					finish();
				}
				else if(type.equalsIgnoreCase("table")) {
					Intent i = new Intent(getApplicationContext(), TableViewDisplay.class);
					startActivity(i);
					finish();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private void showAlert(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Weather App")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public static boolean isEmpty(EditText editText) {

		String input = editText.getText().toString().trim();
		return input.length() == 0;

	}
	private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
				case 1:
					Bundle bundle = message.getData();
					locationAddress = bundle.getString("address");
					break;
				default:
					locationAddress = null;
			}
			latitude.setText(locationAddress);
		}
	}
}
