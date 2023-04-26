package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.test_service.mapper.TestQuestionsMapper;
import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.pojo.TestQuestions;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.service.TestQuestionsService;
import com.kevin.test_service.util.IntegerConstant;
import com.kevin.test_service.util.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 20349
 * @description 针对表【test_questions】的数据库操作Service实现
 * @createDate 2023-04-12 17:28:56
 */
@Service
public class TestQuestionsServiceImpl extends ServiceImpl<TestQuestionsMapper, TestQuestions>
		implements TestQuestionsService {
	RedisTemplate<String, String> redisTemplate;

	TestInfoService testInfoService;

	@Autowired
	public TestQuestionsServiceImpl(RedisTemplate<String, String> redisTemplate, TestInfoService testInfoService) {
		this.redisTemplate = redisTemplate;
		this.testInfoService = testInfoService;
	}

	/**
	 * 根据 tesId 获取试题。优先从缓存获取，其次从数据库读取。
	 */
	@Override
	public TestQuestions getTestById(String testId) {
		String questionJson = redisTemplate.opsForValue().get(StringConstant.REDIS_HOT_TEST_PREFIX + testId);
		if (questionJson == null) {
			//若缓存中不存在，则从数据库中读取
			TestQuestions testQuestions = getById(testId);
			//若是人气试卷，则存入缓存，有效期5小时
			TestInfo testInfo = testInfoService.getById(testId);
			if (testInfo.getStudyNum() > IntegerConstant.HOT_TEST_STANDARD) {
				try {
					questionJson = new ObjectMapper().writeValueAsString(testQuestions);
					redisTemplate.opsForValue().set(StringConstant.REDIS_HOT_TEST_PREFIX + testId, questionJson, 5, TimeUnit.HOURS);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
			return testQuestions;
		} else {
			//若缓存中存在，则直接读取，并刷新有效期（5小时）
			try {
				redisTemplate.opsForValue().set(StringConstant.REDIS_HOT_TEST_PREFIX + testId, questionJson, 5, TimeUnit.HOURS);
				return new ObjectMapper().readValue(questionJson, TestQuestions.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}




