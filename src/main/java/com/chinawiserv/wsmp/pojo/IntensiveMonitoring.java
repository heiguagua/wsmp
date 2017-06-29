package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2017-06-28
 */
@TableName("INTENSIVE_MONITORING")
public class IntensiveMonitoring extends Model<IntensiveMonitoring> {

    private static final long serialVersionUID = 1L;

	@TableField("SINGAL_FREQUENCY")
	private String singalFrequency;

	@TableField(exist = false)
	private int status;

	@Override
	protected Serializable pkVal() {
		return this.getSingalFrequency();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSingalFrequency() {
		return singalFrequency;
	}

	public void setSingalFrequency(String singalFrequency) {
		this.singalFrequency = singalFrequency;
	}

}
