package com.kevin.test_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 试卷答案
 *
 * @TableName test_answer
 */
@TableName(value = "test_answer")
@Data
public class TestAnswer implements Serializable {
	/**
	 *
	 */
	@TableId
	private Integer testId;

	/**
	 *
	 */
	private String selectAnswer;

	/**
	 *
	 */
	private String judgeAnswer;

	/**
	 *
	 */
	private String completeAnswer;

	/**
	 *
	 */
	private String comprehensionAnswer;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}