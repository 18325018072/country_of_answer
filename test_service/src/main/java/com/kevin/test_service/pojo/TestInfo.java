package com.kevin.test_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷信息
 *
 * @TableName test_info
 */
@TableName(value = "test_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestInfo implements Serializable {
	@TableField(exist = false)
	public static final String STATUS_OPEN = "open";
	@TableField(exist = false)
	public static final String STATUS_NOT_OPEN = "notOpen";
	@TableField(exist = false)
	public static final String STATUS_CLOSED = "closed";

	/**
	 * 试卷ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer testId;

	/**
	 * 试卷名
	 */
	private String testName;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 发布者
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

	/**
	 * 开放时间的时间戳
	 */
	@TableField(exist = false)
	private Long openTimeStamp;

	/**
	 * 结束时间的时间戳
	 */
	@TableField(exist = false)
	private Long endTimeStamp;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * 将时间转换为时间戳，便于传输
	 */
	public void loadTimeStamp() {
		if (openTimeStamp == null || endTimeStamp == null) {
			if (openTime != null) {
				openTimeStamp = openTime.getTime();
				endTimeStamp = endTime.getTime();
			}
		}
	}
}