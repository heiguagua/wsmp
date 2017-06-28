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

    public int getFrequencyPeaksNum() {
        return this.frequencyPeaksNum;
    }

    public int getMaxDensity() {
        return this.maxDensity;
    }

    public double getRate() {
        return this.rate;
    }

    public String getS() {
        return this.s;
    }

    public double getStationaryIndex() {
        return this.stationaryIndex;
    }

    public void setMaxDensity(int maxDensity) {
        this.maxDensity = maxDensity;
    }

}
