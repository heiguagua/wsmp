package com.chinawiserv.wsmp.pojo;

public class Signature {

	private int maxDensity;

	private String s;

	private double stationaryIndex;

	private double rate;

	private int frequencyPeaksNum;

	public Signature(int maxDensity, String s, double stationaryIndex, double rate, int frequencyPeaksNum) {
		super();
		this.maxDensity = maxDensity;
		this.s = s;
		this.rate = rate;
		this.stationaryIndex = stationaryIndex;
		this.frequencyPeaksNum = frequencyPeaksNum;
	}

	public int getMaxDensity() {
		return maxDensity;
	}

	public void setMaxDensity(int maxDensity) {
		this.maxDensity = maxDensity;
	}

	public String getS() {
		return s;
	}

	public double getRate() {
		return rate;
	}

	public int getFrequencyPeaksNum() {
		return frequencyPeaksNum;
	}

	public double getStationaryIndex() {
		return stationaryIndex;
	}

}
