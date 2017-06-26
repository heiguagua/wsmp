package com.chinawiserv.wsmp.pojo;


public class RedioType {

	private String legalNormalStation;

	private String illegalNormalStation;

	private String llegalStation;

	private String illegalSignal;

	private String unKonw;
	public RedioType(String legalNormalStation, String illegalNormalStation, String llegalStation, String illegalSignal, String unKonw) {
		this.legalNormalStation = legalNormalStation;
		this.illegalNormalStation = illegalNormalStation;
		this.llegalStation = llegalStation;
		this.illegalSignal = illegalSignal;
		this.unKonw = unKonw;
	}

	public String getLegalNormalStation() {
		return legalNormalStation;
	}

	public void setLegalNormalStation(String legalNormalStation) {
		this.legalNormalStation = legalNormalStation;
	}

	public String getIllegalNormalStation() {
		return illegalNormalStation;
	}

	public void setIllegalNormalStation(String illegalNormalStation) {
		this.illegalNormalStation = illegalNormalStation;
	}

	public String getLlegalStation() {
		return llegalStation;
	}

	public void setLlegalStation(String llegalStation) {
		this.llegalStation = llegalStation;
	}

	public String getIllegalSignal() {
		return illegalSignal;
	}

	public void setIllegalSignal(String illegalSignal) {
		this.illegalSignal = illegalSignal;
	}

	public String getUnKonw() {
		return unKonw;
	}

	public void setUnKonw(String unKonw) {
		this.unKonw = unKonw;
	}

}
