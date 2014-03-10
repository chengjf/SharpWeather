package com.sharp.sharpweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.sharp.sharpweather.bean.Result;
import com.sharp.sharpweather.bean.Weather;

public class CityWeatherParser {
	private String cityName;
	private static URL url;
	private static URLConnection conn;

	public CityWeatherParser(String cityName) {
		this.cityName = cityName;
	}

	public Result parseByJerry() {
		Result result = new Result();
		try {
			Log.i(this.getClass().getName(), "Start Connecting......");
			long t1 = System.currentTimeMillis();
			url = new URL(GlobalConstant.URL_PATTERN_START
					+ URLEncoder.encode(this.cityName)
					+ GlobalConstant.URL_PATTERN_END);
			Log.i(this.getClass().getName(), URLEncoder.encode(this.cityName));
			conn = url.openConnection();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			conn.setDoOutput(true);

			InputStream inputStream = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream, "utf8");

			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(isr, 8 * 1024);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}
			String resultString = sb.toString();
			Log.i(this.getClass().getName(), "End Connecting......");
			long t2 = System.currentTimeMillis();
			Log.i(this.getClass().getName(), "Execute Time is " + (t2 - t1));

			Log.i(this.getClass().getName(), "Start Parsing......");

			JSONTokener jsonTokener = new JSONTokener(resultString);
			JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();

			// 获取是否成功标志
			String status = jsonObject.getString("status");
			result.setStatus(status.equals("success") ? true : false);
			if (status.equals("success")) {
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("Please Check Your City's Code!");
				result.setDetail(status);
				return result;
			}

			// 获取查询时日期
			String date = jsonObject.getString("date");
			result.setDate(date);

			// 结果
			JSONObject r = (JSONObject) jsonObject.getJSONArray("results").get(
					0);

			// 城市
			String city = r.getString("currentCity");
			result.setCity(city);

			//

			JSONArray wArray = r.getJSONArray("weather_data");
			for (int i = 0; i < wArray.length(); i++) {
				JSONObject w = wArray.getJSONObject(i);
				Weather weather = new Weather();
				weather.setDate(w.getString("date"));
				weather.setWeather(w.getString("weather"));
				weather.setWind(w.getString("wind"));
				weather.setTemperature(w.getString("temperature"));
				weather.setDay(w.getString("dayPictureUrl").substring(44));
				weather.setNight(w.getString("nightPictureUrl").substring(46));
				result.getWeathers().add(weather);
			}

		} catch (MalformedURLException e) {
			result.setStatus(false);
			result.setMsg("Please Check Your City's Code!");
			result.setDetail(e.toString());
		} catch (IOException e) {
			result.setStatus(false);
			result.setMsg("Please Check Your Network!");
			result.setDetail(e.toString());
		} catch (Exception e) {
			result.setStatus(false);
			result.setMsg("Sorry That I Loved Your!");
			result.setDetail(e.toString());
		}

		return result;
	}
}
