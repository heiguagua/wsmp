package com.chinawiserv.wsmp.pojo;

public class CommiucationTableTop {

    private String net_type;

    private String operator;

    private String channel;

    private String beginTime;

    private String endTime;

    private long fregMixLong;

    private long fregMaxLong;

    private String tech_way;

    private String channel_use;

    private float info_use;

    private float detect_coverage;

    private String comm_coverage;

    public String getNet_type() {
	return net_type;
    }

    public void setNet_type(String net_type) {
	this.net_type = net_type;
    }

    public String getOperator() {
	return operator;
    }

    public void setOperator(String operator) {
	this.operator = operator;
    }

    public String getChannel() {
	return channel;
    }

    public void setChannel(String channel) {
	this.channel = channel;
    }

    public String getTech_way() {
	return tech_way;
    }

    public void setTech_way(String tech_way) {
	this.tech_way = tech_way;
    }

    public String getComm_coverage() {
	return comm_coverage;
    }

    public void setComm_coverage(String comm_coverage) {
	this.comm_coverage = comm_coverage;
    }

    public long getFregMixLong() {
	return fregMixLong;
    }

    public void setFregMixLong(long fregMixLong) {
	this.fregMixLong = fregMixLong;
    }

    public long getFregMaxLong() {
	return fregMaxLong;
    }

    public void setFregMaxLong(long fregMaxLong) {
	this.fregMaxLong = fregMaxLong;
    }

    public float getDetect_coverage() {
	return detect_coverage;
    }

    public void setDetect_coverage(float detect_coverage) {
	this.detect_coverage = detect_coverage;
    }

    public float getInfo_use() {
	return info_use;
    }

    public void setInfo_use(float info_use) {
	this.info_use = info_use;
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

    public String getChannel_use() {
	return channel_use;
    }

    public void setChannel_use(String channel_use) {
	this.channel_use = channel_use;
    }

}
