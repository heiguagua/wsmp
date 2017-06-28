package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 频段范围表，分为上行频段和下行频段。
 * </p>
 *
 * @author ${author}
 * @since 2017-06-28
 */
@TableName("FREQUENCY_RANGE")
public class FrequencyRange extends Model<FrequencyRange> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
	private String uuid;
    /**
     * 频段最大值
     */
	@TableField("MAX_VALUE")
	private Integer maxValue;
    /**
     * 频段最小值
     */
	@TableField("MIN_VALUE")
	private Integer minValue;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	@Override
	protected Serializable pkVal() {
		return this.uuid;
	}

}
