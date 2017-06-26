package com.chinawiserv.wsmp.pojo;

public class BandStatusTable {

	private String radioName = "";

	private String legalStation = "";

	private String illegalStation = "";

	private String legalSignal = "";

	private String unknownSignal = "";

	private String illegalSignal = "";

	public BandStatusTable() {}


	public BandStatusTable(String radioName, String legalStation, String illegalStation, String legalSignal, String unknownSignal, String illegalSignal) {
		super();
		this.radioName = radioName;
		this.legalStation = legalStation;
		this.illegalStation = illegalStation;
		this.legalSignal = legalSignal;
		this.unknownSignal = unknownSignal;
		this.illegalSignal = illegalSignal;
	}

	public String getRadio_name() {
		return radioName;
	}

	public void setRadio_name(String radio_name) {
		this.radioName = radio_name;
	}

	public String getLegal_station() {
		return legalStation;
	}

	public void setLegalStation(String legal_station) {
		this.legalStation = legal_station;
	}

	public String getIllegal_station() {
		return illegalStation;
	}

	public void setIllegalStation(String illegal_station) {
		this.illegalStation = illegal_station;
	}

	public String getLegal_signal() {
		return legalSignal;
	}

	public void setLegal_signal(String legal_signal) {
		this.legalSignal = legal_signal;
	}

	public String getUnknown_signal() {
		return unknownSignal;
	}

	public void setUnknown_signal(String unknown_signal) {
		this.unknownSignal = unknown_signal;
	}

	public String getIllegal_signal() {
		return illegalSignal;
	}

	public void setIllegal_signal(String illegal_signal) {
		this.illegalSignal = illegal_signal;
	}

}
