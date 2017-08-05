package com.chinawiserv.wsmp.pojo;

public class CommiucationTableTop {

    private String net_type;

    private String operator;

    private String channel;

    private String tech_way;

    private long channel_use;

    private String info_use;

    private String detect_coverage;

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

    public String getInfo_use() {
	return info_use;
    }

    public void setInfo_use(String info_use) {
	this.info_use = info_use;
    }

    public String getDetect_coverage() {
	return detect_coverage;
    }

    public void setDetect_coverage(String detect_coverage) {
	this.detect_coverage = detect_coverage;
    }

    public String getComm_coverage() {
	return comm_coverage;
    }

    public void setComm_coverage(String comm_coverage) {
	this.comm_coverage = comm_coverage;
    }

    public long getChannel_use() {
	return channel_use;
    }

    public void setChannel_use(long channel_use) {
	this.channel_use = channel_use;
    }

}
