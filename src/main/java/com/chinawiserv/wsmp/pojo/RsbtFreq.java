package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("RSBT_FREQ")
public class RsbtFreq extends Model<RsbtFreq> {

    private static final long serialVersionUID = 1L;

    @TableId("GUID")
	private String guid;
	@TableField("Station_GUID")
	private String StationGUID;
	@TableField("FREQ_Type")
	private String FREQType;
	@TableField("FREQ_LC")
	private BigDecimal freqLc;
	@TableField("FREQ_UC")
	private BigDecimal freqUc;
	@TableField("FREQ_EFB")
	private BigDecimal freqEfb;
	@TableField("FREQ_EFE")
	private BigDecimal freqEfe;
	@TableField("FREQ_E_Band")
	private BigDecimal FREQEBand;
	@TableField("FREQ_RFB")
	private BigDecimal freqRfb;
	@TableField("FREQ_RFE")
	private BigDecimal freqRfe;
	@TableField("FREQ_R_Band")
	private BigDecimal FREQRBand;
	@TableField("FREQ_MOD")
	private String freqMod;
	@TableField("FREQ_MB")
	private String freqMb;
	@TableField("FREQ_Code")
	private String FREQCode;


	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getStationGUID() {
		return StationGUID;
	}

	public void setStationGUID(String StationGUID) {
		this.StationGUID = StationGUID;
	}

	public String getFREQType() {
		return FREQType;
	}

	public void setFREQType(String FREQType) {
		this.FREQType = FREQType;
	}

	public BigDecimal getFreqLc() {
		return freqLc;
	}

	public void setFreqLc(BigDecimal freqLc) {
		this.freqLc = freqLc;
	}

	public BigDecimal getFreqUc() {
		return freqUc;
	}

	public void setFreqUc(BigDecimal freqUc) {
		this.freqUc = freqUc;
	}

	public BigDecimal getFreqEfb() {
		return freqEfb;
	}

	public void setFreqEfb(BigDecimal freqEfb) {
		this.freqEfb = freqEfb;
	}

	public BigDecimal getFreqEfe() {
		return freqEfe;
	}

	public void setFreqEfe(BigDecimal freqEfe) {
		this.freqEfe = freqEfe;
	}

	public BigDecimal getFREQEBand() {
		return FREQEBand;
	}

	public void setFREQEBand(BigDecimal FREQEBand) {
		this.FREQEBand = FREQEBand;
	}

	public BigDecimal getFreqRfb() {
		return freqRfb;
	}

	public void setFreqRfb(BigDecimal freqRfb) {
		this.freqRfb = freqRfb;
	}

	public BigDecimal getFreqRfe() {
		return freqRfe;
	}

	public void setFreqRfe(BigDecimal freqRfe) {
		this.freqRfe = freqRfe;
	}

	public BigDecimal getFREQRBand() {
		return FREQRBand;
	}

	public void setFREQRBand(BigDecimal FREQRBand) {
		this.FREQRBand = FREQRBand;
	}

	public String getFreqMod() {
		return freqMod;
	}

	public void setFreqMod(String freqMod) {
		this.freqMod = freqMod;
	}

	public String getFreqMb() {
		return freqMb;
	}

	public void setFreqMb(String freqMb) {
		this.freqMb = freqMb;
	}

	public String getFREQCode() {
		return FREQCode;
	}

	public void setFREQCode(String FREQCode) {
		this.FREQCode = FREQCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.guid;
	}

}
