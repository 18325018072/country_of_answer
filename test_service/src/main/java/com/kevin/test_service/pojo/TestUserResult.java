package com.kevin.test_service.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户-试卷答题情况
 *
 * @TableName test_user_result
 */
@TableName(value = "test_user_result")
@Data
public class TestUserResult implements Serializable {
	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Integer resultId;

	/**
	 *
	 */
	private Integer testId;

	/**
	 *
	 */
	private Integer userId;

	/**
	 *
	 */
	private String userSelectAnswer;

	/**
	 *
	 */
	private String userJudgeAnswer;

	/**
	 *
	 */
	private String userCompleteAnswer;

	/**
	 *
	 */
	private String userComprehensionAnswer;

	/**
	 * 用户答案
	 */
	private String resultDetails;

	/**
	 * 已答题次数
	 */
	private Integer tryTime;

	/**
	 * 上次答题成绩
	 */
	private Integer lastGrade;

	/**
	 * 最佳答题成绩
	 */
	private Integer bestGrade;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}