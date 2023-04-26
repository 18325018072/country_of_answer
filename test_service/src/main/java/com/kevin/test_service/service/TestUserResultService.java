package com.kevin.test_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.test_service.pojo.TestUserResult;

/**
 * @author 20349
 * @description 针对表【test_user_result(用户-试卷答题情况)】的数据库操作Service
 * @createDate 2023-04-19 23:56:00
 */
public interface TestUserResultService extends IService<TestUserResult> {
	/**
	 * 提交试卷
	 */
	void submitAnswer(TestUserResult testUserResult);
}
