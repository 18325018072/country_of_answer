package com.kevin.test_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName test_questions
 */
@TableName(value = "test_questions")
@Data
public class TestQuestions implements Serializable {
	/**
	 *
	 */
	@TableId
	private Integer testId;

	/**
	 *
	 */
	private String selectQuestions;

	/**
	 *
	 */
	private String judgeQuestions;

	/**
	 *
	 */
	private String completeQuestions;

	/**
	 *
	 */
	private String comprehensionQuestions;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}