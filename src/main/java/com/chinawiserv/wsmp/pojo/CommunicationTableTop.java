package com.chinawiserv.wsmp.pojo;

public class CommunicationTableTop {

	//2g-4g
    private String generation;

    //运营商
    private String operator;
    
    //频段
    private String freqRange;

    //技术制式
    private String techName;
    
    //信道数
    private String infoChannel;
    
    //监测站覆盖率
    private String monitorCoverage;
    
    //台站覆盖率
    private String stationCoverage;
    
    //频段占用度
    private String occupancy;

	public String getGeneration() {
		return generation;
	}

	public String getOperator() {
		return operator;
	}

	public String getFreqRange() {
		return freqRange;
	}

	public String getTechName() {
		return techName;
	}

	public String getInfoChannel() {
		return infoChannel;
	}

	public String getMonitorCoverage() {
		return monitorCoverage;
	}

	public String getStationCoverage() {
		return stationCoverage;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setFreqRange(String freqRange) {
		this.freqRange = freqRange;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public void setInfoChannel(String infoChannel) {
		this.infoChannel = infoChannel;
	}

	public void setMonitorCoverage(String monitorCoverage) {
		this.monitorCoverage = monitorCoverage;
	}

	public void setStationCoverage(String stationCoverage) {
		this.stationCoverage = stationCoverage;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

    

}
