package com.chinawiserv.wsmp.pojo;


public class BandStatusTable {

	private String tabletype;

	private String bandName;

	private String legalNormalStation;

	private String legalUnnormalStation;

	private String unKnownSignal;

	private String knownSignal;

	private String illegalSignal;

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public String getLegalNormalStation() {
		return legalNormalStation;
	}

	public void setLegalNormalStation(String legalNormalStation) {
		this.legalNormalStation = legalNormalStation;
	}

	public String getLegalUnnormalStation() {
		return legalUnnormalStation;
	}

	public void setLegalUnnormalStation(String legalUnnormalStation) {
		this.legalUnnormalStation = legalUnnormalStation;
	}

	public String getUnKnownSignal() {
		return unKnownSignal;
	}

	public void setUnKnownSignal(String unKnownSignal) {
		this.unKnownSignal = unKnownSignal;
	}

	public String getKnownSignal() {
		return knownSignal;
	}

	public void setKnownSignal(String knownSignal) {
		this.knownSignal = knownSignal;
	}

	public String getIllegalSignal() {
		return illegalSignal;
	}

	public void setIllegalSignal(String illegalSignal) {
		this.illegalSignal = illegalSignal;
	}

	public String getTabletype() {
		return tabletype;
	}

	public void setTabletype(String tabletype) {
		this.tabletype = tabletype;
	}

}
