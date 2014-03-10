package com.sharp.sharpweather;

import java.lang.reflect.Field;

import com.sharp.sharpweather.bean.Result;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import roboguice.activity.RoboTabActivity;
import roboguice.inject.InjectView;

public class MyTabActivity extends RoboTabActivity implements
		OnTabChangeListener {

	@InjectView(R.id.tab1_date)
	TextView tab1_date;
	@InjectView(R.id.tab1_weather)
	TextView tab1_weather;
	@InjectView(R.id.tab1_wind)
	TextView tab1_wind;
	@InjectView(R.id.tab1_temperature)
	TextView tab1_temperature;
	@InjectView(R.id.tab1_day)
	ImageView tab1_day;
	@InjectView(R.id.tab1_night)
	ImageView tab1_night;

	@InjectView(R.id.tab2_date)
	TextView tab2_date;
	@InjectView(R.id.tab2_weather)
	TextView tab2_weather;
	@InjectView(R.id.tab2_wind)
	TextView tab2_wind;
	@InjectView(R.id.tab2_temperature)
	TextView tab2_temperature;
	@InjectView(R.id.tab2_day)
	ImageView tab2_day;
	@InjectView(R.id.tab2_night)
	ImageView tab2_night;

	@InjectView(R.id.tab3_date)
	TextView tab3_date;
	@InjectView(R.id.tab3_weather)
	TextView tab3_weather;
	@InjectView(R.id.tab3_wind)
	TextView tab3_wind;
	@InjectView(R.id.tab3_temperature)
	TextView tab3_temperature;
	@InjectView(R.id.tab3_day)
	ImageView tab3_day;
	@InjectView(R.id.tab3_night)
	ImageView tab3_night;

	@InjectView(R.id.tab4_date)
	TextView tab4_date;
	@InjectView(R.id.tab4_weather)
	TextView tab4_weather;
	@InjectView(R.id.tab4_wind)
	TextView tab4_wind;
	@InjectView(R.id.tab4_temperature)
	TextView tab4_temperature;
	@InjectView(R.id.tab4_day)
	ImageView tab4_day;
	@InjectView(R.id.tab4_night)
	ImageView tab4_night;

	private Result result;
	final private int ABOUT = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_tab);

		TabHost tabHost = this.getTabHost();
		// LayoutInflater.from(this).inflate(R.layout.android_tab,
		// tabHost.getTabContentView(), true);
		TabSpec tabA = tabHost.newTabSpec("A").setIndicator("今天")
				.setContent(R.id.tab1);
		tabHost.addTab(tabA);
		TabSpec tabB = tabHost.newTabSpec("B").setIndicator("明天")
				.setContent(R.id.tab2);
		tabHost.addTab(tabB);
		TabSpec tabC = tabHost.newTabSpec("C").setIndicator("后天")
				.setContent(R.id.tab3);
		tabHost.addTab(tabC);
		TabSpec tabD = tabHost.newTabSpec("D").setIndicator("大后天")
				.setContent(R.id.tab4);
		tabHost.addTab(tabD);

		tabHost.setOnTabChangedListener(this);
		tabHost.setCurrentTab(0);

		new InitTask().execute("");
	}

	@Override
	public void onTabChanged(String tabId) {
		if (tabId.equals("A")) {

		}
		if (tabId.equals("B")) {

		}
		if (tabId.equals("C")) {

		}
	}

	private class InitTask extends AsyncTask<String, Integer, String> {

		CityWeatherParser parser = new CityWeatherParser("西安");

		@Override
		protected String doInBackground(String... params) {
			Log.i(this.getClass().getName(), "DoInBackground Start!");
			result = parser.parseByJerry();
			Log.i(this.getClass().getName(), "DoInBackground End!");
			return "";
		}

		@Override
		protected void onPostExecute(String resultString) {
			Log.i(this.getClass().getName(), "PostExecute!");
			if (result.getStatus()) {
				Log.i(this.getClass().getName(), "Init UI ......");
				refresh();
				Log.i(this.getClass().getName(), "Init UI END!");
			} else {
				Toast.makeText(getApplicationContext(), result.getDetail(),
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			Log.i(this.getClass().getName(), "ProgressUpdate!");
		}
	}

	private void refresh() {
		if (result.getWeathers().size() == 4) {
			tab1_date.setText(result.getWeathers().get(0).getDate());
			tab1_weather.setText(result.getWeathers().get(0).getWeather());
			tab1_wind.setText(result.getWeathers().get(0).getWind());
			tab1_temperature.setText(result.getWeathers().get(0)
					.getTemperature());
			tab1_day.setImageResource(getIconId("day_"
					+ result.getWeathers().get(0).getDay()));
			tab1_night.setImageResource(getIconId(result.getWeathers().get(0)
					.getNight()));
			Log.i(this.getClass().getName(), result.getWeathers().get(0)
					.getNight());

			tab2_date.setText(result.getWeathers().get(1).getDate());
			tab2_weather.setText(result.getWeathers().get(1).getWeather());
			tab2_wind.setText(result.getWeathers().get(1).getWind());
			tab2_temperature.setText(result.getWeathers().get(1)
					.getTemperature());
			tab2_day.setImageResource(getIconId("day_"
					+ result.getWeathers().get(1).getDay()));
			tab2_night.setImageResource(getIconId(result.getWeathers().get(1)
					.getNight()));
			Log.i(this.getClass().getName(), result.getWeathers().get(1)
					.getNight());

			tab3_date.setText(result.getWeathers().get(2).getDate());
			tab3_weather.setText(result.getWeathers().get(2).getWeather());
			tab3_wind.setText(result.getWeathers().get(2).getWind());
			tab3_temperature.setText(result.getWeathers().get(2)
					.getTemperature());
			tab3_day.setImageResource(getIconId("day_"
					+ result.getWeathers().get(2).getDay()));
			tab3_night.setImageResource(getIconId(result.getWeathers().get(2)
					.getNight()));
			Log.i(this.getClass().getName(), result.getWeathers().get(2)
					.getNight());

			tab4_date.setText(result.getWeathers().get(3).getDate());
			tab4_weather.setText(result.getWeathers().get(3).getWeather());
			tab4_wind.setText(result.getWeathers().get(3).getWind());
			tab4_temperature.setText(result.getWeathers().get(3)
					.getTemperature());
			tab4_day.setImageResource(getIconId("day_"
					+ result.getWeathers().get(3).getDay()));
			tab4_night.setImageResource(getIconId(result.getWeathers().get(3)
					.getNight()));
			Log.i(this.getClass().getName(), result.getWeathers().get(3)
					.getNight());
		}
	}

	private int getIconId(String iconName) {
		Field field;
		int i = 0;
		try {
			field = R.drawable.class.getField(iconName.substring(0,
					iconName.indexOf(".")));
			i = field.getInt(new R.drawable());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ABOUT, 0, "About");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ABOUT:
			AboutDialog dialog = new AboutDialog(this);
			dialog.setTitle("About");
			dialog.show();
			break;

		default:
			break;
		}
		return true;
	}
}
