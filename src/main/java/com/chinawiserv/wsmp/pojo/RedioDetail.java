package com.chinawiserv.wsmp.pojo;

import java.util.List;

public class RedioDetail {

	private String name;

	private float centor;

	private double band;

	private String type;

	private Object rMax;

	private Object specT;

	private Object symRate;

	private Object flatDegree;

	private Object freqPeakNumFSK;
	
	private List<String> monitorID;
	
	private String station;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCentor() {
		return centor;
	}

	public void setCentor(float centor) {
		this.centor = centor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBand() {
		return band;
	}

	public void setBand(double band) {
		this.band = band;
	}

	public Object getSpecT() {
		return specT;
	}

	public void setSpecT(Object specT) {
		this.specT = specT;
	}

	public Object getSymRate() {
		return symRate;
	}

	public void setSymRate(Object symRate) {
		this.symRate = symRate;
	}

	public Object getFlatDegree() {
		return flatDegree;
	}

	public void setFlatDegree(Object flatDegree) {
		this.flatDegree = flatDegree;
	}

	public Object getFreqPeakNumFSK() {
		return freqPeakNumFSK;
	}

	public void setFreqPeakNumFSK(Object freqPeakNumFSK) {
		this.freqPeakNumFSK = freqPeakNumFSK;
	}

	public Object getrMax() {
		return rMax;
	}

	public void setrMax(Object rMax) {
		this.rMax = rMax;
	}

	public List<String> getMonitorID() {
		return monitorID;
	}

	public void setMonitorID(List<String> monitorID) {
		this.monitorID = monitorID;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
}
