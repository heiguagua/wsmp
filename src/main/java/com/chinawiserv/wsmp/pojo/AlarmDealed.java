package com.chinawiserv.wsmp.pojo;

import java.util.List;

public class AlarmDealed {
	private String radio;

	private String firstTime;

	private String lastingTime;

	private String radioType;

	private String station;

	private String radioStatus;

	private String mark;
	
	private List<String> stationID;

	public AlarmDealed() {

	}

	public AlarmDealed(String radio, String firstTime, String lastingTime, String radioType, String station, String radioStatus, String mark) {
		super();
		this.radio = radio;
		this.firstTime = firstTime;
		this.lastingTime = lastingTime;
		this.radioType = radioType;
		this.station = station;
		this.radioStatus = radioStatus;
		this.mark = mark;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getLastingTime() {
		return lastingTime;
	}

	public void setLastingTime(String lastingTime) {
		this.lastingTime = lastingTime;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getRadioStatus() {
		return radioStatus;
	}

	public void setRadioStatus(String radioStatus) {
		this.radioStatus = radioStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public List<String> getStationID() {
		return stationID;
	}

	public void setStationID(List<String> stationID) {
		this.stationID = stationID;
	}
}
