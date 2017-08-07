package com.chinawiserv.wsmp.pojo;

import java.util.Date;
import java.util.UUID;

public class MeasureTaskParamDto {
	
	private UUID ID;
	private String warnID;
	private double beginFreq;
	private double endFreq;
	//执行时长(秒)
	private int Duration;
	private String BeginTime;
	private String endTime;
	//循环周期(分钟)
	private int CycleStep;
	private boolean isFreqRange;
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
	private int totalIQCout;
	//总频谱帧数
	private int totalSpecCout;
	//总声音时间
	private int totalAudioTimespan;
	//总ITU帧数
	private int totalITUCount;
	//总特征数
	private int totalFreatureCount;
	public double getBeginFreq() {
		return beginFreq;
	}
	public double getEndFreq() {
		return endFreq;
	}
	public int getDuration() {
		return Duration;
	}
	public String getBeginTime() {
		return BeginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public int getCycleStep() {
		return CycleStep;
	}
	public boolean isFreqRange() {
		return isFreqRange;
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
	public UUID getID() {
		return ID;
	}
	public String getWarnID() {
		return warnID;
	}
	public void setID(UUID iD) {
		ID = iD;
	}
	public void setWarnID(String warnID) {
		this.warnID = warnID;
	}
	public int getFeatureCount() {
		return featureCount;
	}
	public int getTotalIQCout() {
		return totalIQCout;
	}
	public int getTotalSpecCout() {
		return totalSpecCout;
	}
	public int getTotalAudioTimespan() {
		return totalAudioTimespan;
	}
	public int getTotalITUCount() {
		return totalITUCount;
	}
	public int getTotalFreatureCount() {
		return totalFreatureCount;
	}
	public void setBeginFreq(double beginFreq) {
		this.beginFreq = beginFreq;
	}
	public void setEndFreq(double endFreq) {
		this.endFreq = endFreq;
	}
	public void setDuration(int duration) {
		Duration = duration;
	}
	public void setBeginTime(String beginTime) {
		BeginTime = beginTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setCycleStep(int cycleStep) {
		CycleStep = cycleStep;
	}
	public void setFreqRange(boolean isFreqRange) {
		this.isFreqRange = isFreqRange;
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
	public void setTotalIQCout(int totalIQCout) {
		this.totalIQCout = totalIQCout;
	}
	public void setTotalSpecCout(int totalSpecCout) {
		this.totalSpecCout = totalSpecCout;
	}
	public void setTotalAudioTimespan(int totalAudioTimespan) {
		this.totalAudioTimespan = totalAudioTimespan;
	}
	public void setTotalITUCount(int totalITUCount) {
		this.totalITUCount = totalITUCount;
	}
	public void setTotalFreatureCount(int totalFreatureCount) {
		this.totalFreatureCount = totalFreatureCount;
	}
	
}
