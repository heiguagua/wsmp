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

	public String getIllegalNormalStation() {
		return this.illegalNormalStation;
	}

	public String getIllegalSignal() {
		return this.illegalSignal;
	}

	public String getLegalNormalStation() {
		return this.legalNormalStation;
	}

	public String getLlegalStation() {
		return this.llegalStation;
	}

	public String getUnKonw() {
		return this.unKonw;
	}

	public void setIllegalNormalStation(String illegalNormalStation) {
		this.illegalNormalStation = illegalNormalStation;
	}

	public void setIllegalSignal(String illegalSignal) {
		this.illegalSignal = illegalSignal;
	}

	public void setLegalNormalStation(String legalNormalStation) {
		this.legalNormalStation = legalNormalStation;
	}

	public void setLlegalStation(String llegalStation) {
		this.llegalStation = llegalStation;
	}

	public void setUnKonw(String unKonw) {
		this.unKonw = unKonw;
	}

}
