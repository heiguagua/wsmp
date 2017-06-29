package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2017-06-29
 */
@TableName("RSBT_STATION")
public class RsbtStation extends Model<RsbtStation> {

    private static final long serialVersionUID = 1L;

    @TableId("GUI")
	private String gui;
	@TableField("NET_GUID")
	private String netGuid;
	@TableField("ORG_Code")
	private String ORGCode;
	@TableField("APP_Code")
	private String APPCode;
	@TableField("STAT_APP_Type")
	private String STATAPPType;
	@TableField("STAT_TDI")
	private String statTdi;
	@TableField("STAT_Name")
	private String STATName;
	@TableField("STAT_ADDR")
	private String statAddr;
	@TableField("STAT_Area_Code")
	private String STATAreaCode;
	@TableField("STAT_Type")
	private String STATType;
	@TableField("STAT_Work")
	private String STATWork;
	@TableField("STAT_Status")
	private String STATStatus;
	@TableField("STAT_EQU_SUM")
	private BigDecimal statEquSum;
	@TableField("STAT_LG")
	private BigDecimal statLg;
	@TableField("STAT_LA")
	private BigDecimal statLa;
	@TableField("STAT_AT")
	private BigDecimal statAt;
	@TableField("STAT_Date_Start")
	private Date STATDateStart;
	@TableField("MEMO")
	private String memo;


	public String getGui() {
		return gui;
	}

	public void setGui(String gui) {
		this.gui = gui;
	}

	public String getNetGuid() {
		return netGuid;
	}

	public void setNetGuid(String netGuid) {
		this.netGuid = netGuid;
	}

	public String getORGCode() {
		return ORGCode;
	}

	public void setORGCode(String ORGCode) {
		this.ORGCode = ORGCode;
	}

	public String getAPPCode() {
		return APPCode;
	}

	public void setAPPCode(String APPCode) {
		this.APPCode = APPCode;
	}

	public String getSTATAPPType() {
		return STATAPPType;
	}

	public void setSTATAPPType(String STATAPPType) {
		this.STATAPPType = STATAPPType;
	}

	public String getStatTdi() {
		return statTdi;
	}

	public void setStatTdi(String statTdi) {
		this.statTdi = statTdi;
	}

	public String getSTATName() {
		return STATName;
	}

	public void setSTATName(String STATName) {
		this.STATName = STATName;
	}

	public String getStatAddr() {
		return statAddr;
	}

	public void setStatAddr(String statAddr) {
		this.statAddr = statAddr;
	}

	public String getSTATAreaCode() {
		return STATAreaCode;
	}

	public void setSTATAreaCode(String STATAreaCode) {
		this.STATAreaCode = STATAreaCode;
	}

	public String getSTATType() {
		return STATType;
	}

	public void setSTATType(String STATType) {
		this.STATType = STATType;
	}

	public String getSTATWork() {
		return STATWork;
	}

	public void setSTATWork(String STATWork) {
		this.STATWork = STATWork;
	}

	public String getSTATStatus() {
		return STATStatus;
	}

	public void setSTATStatus(String STATStatus) {
		this.STATStatus = STATStatus;
	}

	public BigDecimal getStatEquSum() {
		return statEquSum;
	}

	public void setStatEquSum(BigDecimal statEquSum) {
		this.statEquSum = statEquSum;
	}

	public BigDecimal getStatLg() {
		return statLg;
	}

	public void setStatLg(BigDecimal statLg) {
		this.statLg = statLg;
	}

	public BigDecimal getStatLa() {
		return statLa;
	}

	public void setStatLa(BigDecimal statLa) {
		this.statLa = statLa;
	}

	public BigDecimal getStatAt() {
		return statAt;
	}

	public void setStatAt(BigDecimal statAt) {
		this.statAt = statAt;
	}

	public Date getSTATDateStart() {
		return STATDateStart;
	}

	public void setSTATDateStart(Date STATDateStart) {
		this.STATDateStart = STATDateStart;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	protected Serializable pkVal() {
		return this.gui;
	}

}
