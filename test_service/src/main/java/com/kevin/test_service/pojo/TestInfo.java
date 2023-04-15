package com.kevin.test_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 试卷信息
 *
 * @TableName test_info
 */
@TableName(value = "test_info")
@Data
public class TestInfo implements Serializable {
	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Integer testId;

	/**
	 *
	 */
	private String testName;

	/**
	 *
	 */
	private String description;

	/**
	 *
	 */
	private String publisher;

	/**
	 * 试卷状态：notOpen、open、closed
	 */
	private String status;

	/**
	 * 可答题次数，-1为无限
	 */
	private Integer tryLimit;

	/**
	 * 开放时间
	 */
	private Date openTime;

	/**
	 * 关闭时间
	 */
	private Date endTime;

	/**
	 *
	 */
	private String difficulty;

	/**
	 *
	 */
	private Integer studyNum;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}