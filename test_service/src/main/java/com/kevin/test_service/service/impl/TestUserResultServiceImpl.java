package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.test_service.mapper.TestUserResultMapper;
import com.kevin.test_service.pojo.TestUserResult;
import com.kevin.test_service.service.TestUserResultService;
import org.springframework.stereotype.Service;

/**
 * @author 20349
 * @description 针对表【test_user_result(用户-试卷答题情况)】的数据库操作Service实现
 * @createDate 2023-04-19 23:56:00
 */
@Service
public class TestUserResultServiceImpl extends ServiceImpl<TestUserResultMapper, TestUserResult>
		implements TestUserResultService {

	/**
	 * 提交试卷
	 */
	@Override
	public void submitAnswer(TestUserResult testUserResult) {
		//获取用户答题信息
		QueryWrapper<TestUserResult> wrapper = new QueryWrapper<>();
		wrapper.eq("test_id", testUserResult.getTestId());
		wrapper.eq("user_id", testUserResult.getUserId());
		TestUserResult oldResult = getOne(wrapper);
		if (oldResult == null) {
			//创建用户答题信息
			testUserResult.setTryTime(1);
			testUserResult.setIsScoring(0);
			save(testUserResult);
		} else {
			UpdateWrapper<TestUserResult> updateWrapper = new UpdateWrapper<>(oldResult);
			updateWrapper.set("user_select_answer", testUserResult.getUserSelectAnswer());
			updateWrapper.set("user_judge_answer", testUserResult.getUserJudgeAnswer());
			updateWrapper.set("user_complete_answer", testUserResult.getUserCompleteAnswer());
			updateWrapper.set("user_comprehension_answer", testUserResult.getUserComprehensionAnswer());
			updateWrapper.set("try_time", oldResult.getTryTime() + 1);
			updateWrapper.set("is_scoring", 0);
			update(updateWrapper);
		}
	}

	/**
	 * 用于测试提交试卷。提交后，用户试卷状态为已批改，尝试次数为2。
	 */
	@Override
	public void testSubmitAnswer(TestUserResult testUserResult) {
		//获取用户答题信息
		QueryWrapper<TestUserResult> wrapper = new QueryWrapper<>();
		wrapper.eq("test_id", testUserResult.getTestId());
		wrapper.eq("user_id", testUserResult.getUserId());
		TestUserResult oldResult = getOne(wrapper);
		if (oldResult == null) {
			//创建用户答题信息
			testUserResult.setTryTime(2);
			testUserResult.setIsScoring(1);
			save(testUserResult);
		} else {
			UpdateWrapper<TestUserResult> updateWrapper = new UpdateWrapper<>(oldResult);
			updateWrapper.set("user_select_answer", testUserResult.getUserSelectAnswer());
			updateWrapper.set("user_judge_answer", testUserResult.getUserJudgeAnswer());
			updateWrapper.set("user_complete_answer", testUserResult.getUserCompleteAnswer());
			updateWrapper.set("user_comprehension_answer", testUserResult.getUserComprehensionAnswer());
			updateWrapper.set("try_time", 2);
			updateWrapper.set("is_scoring", 1);
			update(updateWrapper);
		}
	}
}




