package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户-试卷答题情况
 *
 * @TableName test_user_result
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestUserResult implements Serializable {
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

	/**
	 * 是否正在评分:0是
	 */
	private Integer isScoring;

	private static final long serialVersionUID = 1L;
}