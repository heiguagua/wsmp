//package com.chinawiserv.wsmp.pojo;
//
//import java.io.Serializable;
//
//import com.baomidou.mybatisplus.activerecord.Model;
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//
///**
// * <p>
// * 
// * </p>
// *
// * @author ${author}
// * @since 2017-06-28
// */
//public class Operator extends Model<Operator> {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 运营商名称
//     */
//	@TableField("NAME")
//	private String name;
//    /**
//     * UUID
//     */
//    @TableId("UUID")
//	private String uuid;
//
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getUuid() {
//		return uuid;
//	}
//
//	public void setUuid(String uuid) {
//		this.uuid = uuid;
//	}
//
//	@Override
//	protected Serializable pkVal() {
//		return this.uuid;
//	}
//
//}
