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
@TableName("KEY_MONITORING")
public class KeyMonitoring extends Model<KeyMonitoring> {

    private static final long serialVersionUID = 1L;


	@TableId
	@TableField("SIGNAL_ID")
	private String signalId;


	public String getSignalId() {
		return signalId;
	}

	public void setSignalId(String signalId) {
		this.signalId = signalId;
	}

	@Override
	protected Serializable pkVal() {
		return this.signalId;
	}

}
