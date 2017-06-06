package com.chinawiserv.radio.business.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("test")
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2090614256377583133L;

	@TableId
	private int id = 1;

	/** 用户名 */
	private String name;

	/** 用户年龄 */
	private Integer s;

	@TableField(exist = false)
	private String state;
}
