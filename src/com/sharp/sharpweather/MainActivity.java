package com.sharp.sharpweather;

import java.net.URLEncoder;

import com.sharp.sharpweather.bean.Result;
import com.sharp.sharpweather.bean.Weather;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.refresh)
	Button refresh;
	@InjectView(R.id.progressBar)
	ProgressBar bar;
	
	@InjectView(R.id.tableLayout)
	TableLayout tableLayout;

	int width;
	int height;

	final private int ABOUT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		height = wm.getDefaultDisplay().getHeight();
		
		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bar.setVisibility(View.VISIBLE);
				bar.setBackgroundColor(Color.BLACK);
				refresh.setEnabled(false);
				InitTask child = new InitTask();
				child.execute("");
			}
		});
		
		bar.setVisibility(View.VISIBLE);
		bar.setBackgroundColor(Color.BLACK);
		refresh.setEnabled(false);
		InitTask child = new InitTask();
		child.execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ABOUT, 0, "About");
		return true;
	}

	private class InitTask extends AsyncTask<String, Integer, String> {

		String city = URLEncoder.encode("西安");
		CityWeatherParser parser = new CityWeatherParser("%E8%A5%BF%E5%AE%89");
		Result result;

		@Override
		protected String doInBackground(String... params) {
			Log.e("city", city);
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
				int size = result.getWeathers().size();
				tableLayout.removeAllViews();
				for(int i = 0; i < size; i++){
					TextView view = new TextView(getApplicationContext());
					TableRow row = new TableRow(getApplicationContext());
					Weather weather = result.getWeathers().get(i);
					
					view = new TextView(getApplicationContext());
					view.setText(weather.getDate());
					view.setTextColor(Color.BLACK);
					view.setGravity(1);
					row.addView(view);
					
					view = new TextView(getApplicationContext());
					view.setText(weather.getWeather());
					view.setTextColor(Color.BLACK);
					view.setGravity(1);
					row.addView(view);
					
					view = new TextView(getApplicationContext());
					view.setText(weather.getWind());
					view.setTextColor(Color.BLACK);
					view.setGravity(1);
					row.addView(view);
					
					view = new TextView(getApplicationContext());
					view.setText(weather.getTemperature());
					view.setTextColor(Color.BLACK);
					view.setGravity(1);
					row.addView(view);
					
					tableLayout.addView(row, i);
				}
				Log.i(this.getClass().getName(), "Init UI END!");
			} else {
				Toast.makeText(getApplicationContext(), result.getDetail(),
						Toast.LENGTH_LONG).show();
			}
			bar.setVisibility(View.GONE);
			refresh.setEnabled(true);
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			Log.i(this.getClass().getName(), "ProgressUpdate!");
		}
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
