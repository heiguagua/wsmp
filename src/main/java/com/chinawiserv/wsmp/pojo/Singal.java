package com.chinawiserv.wsmp.pojo;

import org.springframework.util.StringUtils;

import java.math.BigInteger;

public class Singal {

	private  String stationKey;

	private String context;

	private String centorFreq;

	private String text;

	private String stationNumber;

	private String id;

	private BigInteger integer;

	private String listString;

	private String endTime;

	private int status;

	private String beginTime;

	private String warnimgId;

	private  String des =" ";

	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {

		if (!StringUtils.isEmpty(des)){
			this.des = des;
		}
	}

	public String getWarnimgId() {
		return warnimgId;
	}

	public void setWarnimgId(String warnimgId) {
		this.warnimgId = warnimgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public BigInteger getInteger() {
		return integer;
	}

	public void setInteger(BigInteger integer) {
		this.integer = integer;
	}

	public String getListString() {
		return listString;
	}

	public void setListString(String listString) {
		this.listString = listString;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int i) {
		// TODO Auto-generated method stub
		this.status = i;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCentorFreq() {
		return centorFreq;
	}

	public void setCentorFreq(String centorFreq) {
		this.centorFreq = centorFreq;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

    public void setStation(String stationNumber) {
		this.stationNumber = stationNumber;
    }

	public String getStationNumber() {
		return stationNumber;
	}

	public String getStationKey() {
		return stationKey;
	}

	public void setStationKey(String stationKey) {
		this.stationKey = stationKey;
	}
}
