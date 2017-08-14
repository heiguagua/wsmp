package com.chinawiserv.wsmp.pojo;

public class MeasureTaskParamDto {
	
	private String ID;
	private String warnID;
	private Double beginFreq;
	private Double endFreq;
	//执行时长(秒)
	private Integer duration;
	private String beginTime;
	private String endTime;
	//循环周期(分钟)
	private Integer cycleStep;
	private Boolean freqRange;
	//每次IQ帧数
	private Integer IQCount;
	//每次频谱帧数
	private Integer specCount;
	//每次声音时间
	private Integer audioTimespan;
	//每次ITU帧数
	private Integer ITUCount;
	//每次特征帧数
	private Integer featureCount;
	//总IQ帧数
	private Integer totalIQCount;
	//总频谱帧数
	private Integer totalSpecCount;
	//总声音时间
	private Integer totalAudioTimespan;
	//总ITU帧数
	private Integer totalITUCount;
	//总特征数
	private Integer totalFeatureCount;
	public String getID() {
		return ID;
	}
	public String getWarnID() {
		return warnID;
	}
	public Double getBeginFreq() {
		return beginFreq;
	}
	public Double getEndFreq() {
		return endFreq;
	}
	public Integer getDuration() {
		return duration;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public Integer getCycleStep() {
		return cycleStep;
	}
	public Boolean isFreqRange() {
		return freqRange;
	}
	public Integer getIQCount() {
		return IQCount;
	}
	public Integer getSpecCount() {
		return specCount;
	}
	public Integer getAudioTimespan() {
		return audioTimespan;
	}
	public Integer getITUCount() {
		return ITUCount;
	}
	public Integer getFeatureCount() {
		return featureCount;
	}
	public Integer getTotalIQCount() {
		return totalIQCount;
	}
	public Integer getTotalSpecCount() {
		return totalSpecCount;
	}
	public Integer getTotalAudioTimespan() {
		return totalAudioTimespan;
	}
	public Integer getTotalITUCount() {
		return totalITUCount;
	}
	public Integer getTotalFeatureCount() {
		return totalFeatureCount;
	}
	public void setID(String iD) {
		this.ID = iD;
	}
	public void setWarnID(String warnID) {
		this.warnID = warnID;
	}
	public void setBeginFreq(Double beginFreq) {
		this.beginFreq = beginFreq;
	}
	public void setEndFreq(Double endFreq) {
		this.endFreq = endFreq;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime.replace('T',' ');
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime.replace('T',' ');
	}
	public void setCycleStep(Integer cycleStep) {
		this.cycleStep = cycleStep;
	}
	public void setFreqRange(Boolean freqRange) {
		this.freqRange = freqRange;
	}
	public void setIQCount(Integer iQCount) {
		IQCount = iQCount;
	}
	public void setSpecCount(Integer specCount) {
		this.specCount = specCount;
	}
	public void setAudioTimespan(Integer audioTimespan) {
		this.audioTimespan = audioTimespan;
	}
	public void setITUCount(Integer iTUCount) {
		ITUCount = iTUCount;
	}
	public void setFeatureCount(Integer featureCount) {
		this.featureCount = featureCount;
	}
	public void setTotalIQCount(Integer totalIQCount) {
		this.totalIQCount = totalIQCount;
	}
	public void setTotalSpecCount(Integer totalSpecCount) {
		this.totalSpecCount = totalSpecCount;
	}
	public void setTotalAudioTimespan(Integer totalAudioTimespan) {
		this.totalAudioTimespan = totalAudioTimespan;
	}
	public void setTotalITUCount(Integer totalITUCount) {
		this.totalITUCount = totalITUCount;
	}
	public void setTotalFeatureCount(Integer totalFeatureCount) {
		this.totalFeatureCount = totalFeatureCount;
	}
	
}
