package com.chinawiserv.wsmp.pojo;

public class MeasureTaskParamDto {
	
	private String ID;
	private String warnID;
	private double beginFreq;
	private double endFreq;
	//执行时长(秒)
	private int duration;
	private String beginTime;
	private String endTime;
	//循环周期(分钟)
	private int cycleStep;
	private boolean freqRange;
	//每次IQ帧数
	private int IQCount;
	//每次频谱帧数
	private int specCount;
	//每次声音时间
	private int audioTimespan;
	//每次ITU帧数
	private int ITUCount;
	//每次特征帧数
	private int featureCount;
	//总IQ帧数
	private int totalIQCount;
	//总频谱帧数
	private int totalSpecCount;
	//总声音时间
	private int totalAudioTimespan;
	//总ITU帧数
	private int totalITUCount;
	//总特征数
	private int totalFeatureCount;
	public String getID() {
		return ID;
	}
	public String getWarnID() {
		return warnID;
	}
	public double getBeginFreq() {
		return beginFreq;
	}
	public double getEndFreq() {
		return endFreq;
	}
	public int getDuration() {
		return duration;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public int getCycleStep() {
		return cycleStep;
	}
	public int getIQCount() {
		return IQCount;
	}
	public int getSpecCount() {
		return specCount;
	}
	public int getAudioTimespan() {
		return audioTimespan;
	}
	public int getITUCount() {
		return ITUCount;
	}
	public int getFeatureCount() {
		return featureCount;
	}

	public int getTotalAudioTimespan() {
		return totalAudioTimespan;
	}
	public int getTotalITUCount() {
		return totalITUCount;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public void setWarnID(String warnID) {
		this.warnID = warnID;
	}
	public void setBeginFreq(double beginFreq) {
		this.beginFreq = beginFreq;
	}
	public void setEndFreq(double endFreq) {
		this.endFreq = endFreq;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setCycleStep(int cycleStep) {
		this.cycleStep = cycleStep;
	}
	public void setIQCount(int iQCount) {
		IQCount = iQCount;
	}
	public void setSpecCount(int specCount) {
		this.specCount = specCount;
	}
	public void setAudioTimespan(int audioTimespan) {
		this.audioTimespan = audioTimespan;
	}
	public void setITUCount(int iTUCount) {
		ITUCount = iTUCount;
	}
	public void setFeatureCount(int featureCount) {
		this.featureCount = featureCount;
	}

	public void setTotalAudioTimespan(int totalAudioTimespan) {
		this.totalAudioTimespan = totalAudioTimespan;
	}
	public void setTotalITUCount(int totalITUCount) {
		this.totalITUCount = totalITUCount;
	}
	public boolean isFreqRange() {
		return freqRange;
	}
	public void setFreqRange(boolean freqRange) {
		this.freqRange = freqRange;
	}
	public int getTotalIQCount() {
		return totalIQCount;
	}
	public void setTotalIQCount(int totalIQCount) {
		this.totalIQCount = totalIQCount;
	}
	public int getTotalSpecCount() {
		return totalSpecCount;
	}
	public void setTotalSpecCount(int totalSpecCount) {
		this.totalSpecCount = totalSpecCount;
	}
	public int getTotalFeatureCount() {
		return totalFeatureCount;
	}
	public void setTotalFeatureCount(int totalFeatureCount) {
		this.totalFeatureCount = totalFeatureCount;
	}
	
}
