package com.kevin.test_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.test_service.pojo.TestQuestions;

/**
 * @author 20349
 * @description 针对表【test_questions】的数据库操作Service
 * @createDate 2023-04-12 17:28:56
 */
public interface TestQuestionsService extends IService<TestQuestions> {

	/**
	 * 根据 tesId 获取试题
	 */
	TestQuestions getTestById(String testId);
}
