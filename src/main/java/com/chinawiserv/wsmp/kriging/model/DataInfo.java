package com.chinawiserv.wsmp.kriging.model;

public class DataInfo{
	private double Lon;
	private double Lat;
	private double High;
	private double Value;
	public DataInfo(){}
    public DataInfo(double lo, double la, double hi, double va)
    {
        this.Lon = lo;
        this.Lat = la;
        this.High = hi;
        this.Value = va;
    }
    public DataInfo(double lo, double la, double va)
    {
        this.Lon = lo;
        this.Lat = la;
        this.High = 0.0;
        this.Value = va;
    }

	public double getLon() {
		return Lon;
	}

	public void setLon(double lon) {
		Lon = lon;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getHigh() {
		return High;
	}

	public void setHigh(double high) {
		High = high;
	}

	public double getValue() {
		return Value;
	}

	public void setValue(double value) {
		Value = value;
	}
	@Override
	public String toString() {
		return "DataInfo [x=" + Lat + ", y=" + Lon + ", z=" + Value + "] \n";
	}
    
}
