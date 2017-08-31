package com.chinawiserv.wsmp.pojo;

import java.math.BigInteger;

public class RedioStatusCount {

	// 频段名称
	private String redioName;
	
	// 频段起始频率
	private BigInteger beginFreq;
	
	// 频段结束频率
	private BigInteger endFreq;

	// 合法正常台站
	private int legalNormalStationNumber;

	// 合法违规台站
	private int legalUnNormalStationNumber;

	// 已知信号
	private int konwStationNumber;

	// 不明信号
	private int unKonw;

	// 非法信号
	private int illegalSignal;
	
	//是否有重点监测信息
	private Boolean importantMonitor;

	public String getRedioName() {
		return redioName;
	}

	public void setRedioName(String redioName) {
		this.redioName = redioName;
	}

	public int getLegalNormalStationNumber() {
		return legalNormalStationNumber;
	}

	public void setLegalNormalStationNumber(int legalNormalStationNumber) {
		this.legalNormalStationNumber = legalNormalStationNumber;
	}

	public int getLegalUnNormalStationNumber() {
		return legalUnNormalStationNumber;
	}

	public void setLegalUnNormalStationNumber(int legalUnNormalStationNumber) {
		this.legalUnNormalStationNumber = legalUnNormalStationNumber;
	}

	public int getKonwStationNumber() {
		return konwStationNumber;
	}

	public void setKonwStationNumber(int konwStationNumber) {
		this.konwStationNumber = konwStationNumber;
	}

	public int getUnKonw() {
		return unKonw;
	}

	public void setUnKonw(int unKonw) {
		this.unKonw = unKonw;
	}

	public int getIllegalSignal() {
		return illegalSignal;
	}

	public void setIllegalSignal(int illegalSignal) {
		this.illegalSignal = illegalSignal;
	}

	public BigInteger getBeginFreq() {
		return beginFreq;
	}

	public BigInteger getEndFreq() {
		return endFreq;
	}

	public void setBeginFreq(BigInteger beginFreq) {
		this.beginFreq = beginFreq;
	}

	public void setEndFreq(BigInteger endFreq) {
		this.endFreq = endFreq;
	}

	public Boolean getImportantMonitor() {
		return importantMonitor;
	}

	public void setImportantMonitor(Boolean importantMonitor) {
		this.importantMonitor = importantMonitor;
	}


}
