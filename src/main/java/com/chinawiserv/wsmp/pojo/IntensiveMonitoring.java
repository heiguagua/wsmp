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

	@TableField("SINGAL_ID")
	private String singalId;

	@TableField(exist = false)
	private int status;

	public String getSingalId() {
		return singalId;
	}

	public void setSingalId(String singalId) {

		this.singalId = singalId;
	}

	@Override
	protected Serializable pkVal() {
		return this.singalId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
