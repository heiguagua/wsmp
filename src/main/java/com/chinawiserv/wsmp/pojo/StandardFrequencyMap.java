package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;

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
 * @since 2017-06-28
 */
@TableName("STANDARD_FREQUENCY_MAP")
public class StandardFrequencyMap extends Model<StandardFrequencyMap> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
	private String uuid;
	@TableField("STANDARD_ID")
	private String standardId;
	@TableField("UPSTREAM_RANG_UUID")
	private String upstreamRangUuid;
	@TableField("DOWNSTREAM_RANG_UUID")
	private String downstreamRangUuid;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public String getUpstreamRangUuid() {
		return upstreamRangUuid;
	}

	public void setUpstreamRangUuid(String upstreamRangUuid) {
		this.upstreamRangUuid = upstreamRangUuid;
	}

	public String getDownstreamRangUuid() {
		return downstreamRangUuid;
	}

	public void setDownstreamRangUuid(String downstreamRangUuid) {
		this.downstreamRangUuid = downstreamRangUuid;
	}

	@Override
	protected Serializable pkVal() {
		return this.uuid;
	}

}
