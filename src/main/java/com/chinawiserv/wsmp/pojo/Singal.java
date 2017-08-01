package com.chinawiserv.wsmp.pojo;

import java.math.BigInteger;

public class Singal {

	private String context;

	private String centorFreq;

	private String text;

	private String id;

	private BigInteger integer;

	private String listString;

	private String endTime;

	private int status;

	private String beginTime;

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
}