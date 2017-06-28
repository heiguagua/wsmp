package com.chinawiserv.wsmp.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2017-06-28
 */
public class Standard extends Model<Standard> {

    private static final long serialVersionUID = 1L;

	@TableId
	private String uuid;
    /**
     * 制式名
     */
	@TableField("STANDARD_NAME")
	private String standardName;
    /**
     * 归属运营商
     */
	@TableField("BLONG_OPERATOR")
	private String blongOperator;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getBlongOperator() {
		return blongOperator;
	}

	public void setBlongOperator(String blongOperator) {
		this.blongOperator = blongOperator;
	}

	@Override
	protected Serializable pkVal() {
		return this.uuid;
	}

}
