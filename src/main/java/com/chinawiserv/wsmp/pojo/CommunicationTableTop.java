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
    private Double infoChannel;
    
    //监测站覆盖率
    private Double monitorCoverage;
    
    //台站覆盖率
    private Double stationCoverage;
    
    //频段占用度
    private Double occupancy;

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


	public Double getMonitorCoverage() {
		return monitorCoverage;
	}

	public Double getStationCoverage() {
		return stationCoverage;
	}

	public Double getOccupancy() {
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


	public void setMonitorCoverage(Double monitorCoverage) {
		this.monitorCoverage = monitorCoverage;
	}

	public void setStationCoverage(Double stationCoverage) {
		this.stationCoverage = stationCoverage;
	}

	public void setOccupancy(Double occupancy) {
		this.occupancy = occupancy;
	}

	public Double getInfoChannel() {
		return infoChannel;
	}

	public void setInfoChannel(Double infoChannel) {
		this.infoChannel = infoChannel;
	}
    

}
