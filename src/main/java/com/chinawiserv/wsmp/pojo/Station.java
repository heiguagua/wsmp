package com.chinawiserv.wsmp.pojo;

public class Station {

	private String id;

	private String stationName;

	private String centerFrequency;

	private String tapeWidth;

	public Station(String id, String stationName, String centerFrequency, String tapeWidth) {
		super();
		this.id = id;
		this.stationName = stationName;
		this.setCenterFrequency(centerFrequency);
		this.setTapeWidth(tapeWidth);
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getCenterFrequency() {
		return centerFrequency;
	}

	public void setCenterFrequency(String centerFrequency) {
		this.centerFrequency = centerFrequency;
	}

	public String getTapeWidth() {
		try {
			return new Double(Double.parseDouble(tapeWidth)*1000).toString();
		} catch (NumberFormatException e) {
			return tapeWidth;
		}
	}

	public void setTapeWidth(String tapeWidth) {
		this.tapeWidth = tapeWidth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
