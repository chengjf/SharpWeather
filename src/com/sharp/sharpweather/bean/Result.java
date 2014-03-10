package com.sharp.sharpweather.bean;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private List<Weather> weathers =  new ArrayList<Weather>();
	private boolean status;
	private String msg;
	private String detail;
	private String date;
	private String city;
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Weather> getWeathers() {
		return weathers;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
