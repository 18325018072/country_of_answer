package com.kevin.test_service.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kevin.test_service.pojo.BaseResponsePack;
import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.pojo.TestQuestions;
import com.kevin.test_service.pojo.TestUserResult;
import com.kevin.test_service.service.HotTestService;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.service.TestQuestionsService;
import com.kevin.test_service.service.TestUserResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TestServiceController {
	HotTestService hotTestService;
	TestInfoService testInfoService;
	TestUserResultService testUserResultService;
	TestQuestionsService testQuestionsService;

	@Autowired
	public TestServiceController(HotTestService hotTestService, TestInfoService testInfoService, TestUserResultService testUserResultService, TestQuestionsService testQuestionsService) {
		this.hotTestService = hotTestService;
		this.testInfoService = testInfoService;
		this.testUserResultService = testUserResultService;
		this.testQuestionsService = testQuestionsService;
	}

	/**
	 * 获取试卷信息
	 */
	@GetMapping("getTestInfo")
	public BaseResponsePack getTestInfo(@RequestParam String testId) {
		TestInfo testInfo = testInfoService.getById(testId);
		if (testInfo == null) {
			return BaseResponsePack.simpleFail("试卷不存在");
		} else {
			testInfo.loadTimeStamp();
			return BaseResponsePack.simpleSuccess(testInfo);
		}
	}

	/**
	 * 获取热门试卷
	 */
	@GetMapping("hotTest")
	public BaseResponsePack getHotTest() {
		return BaseResponsePack.simpleSuccess(hotTestService.getHotTestInfo());
	}

	/**
	 * 获取用户答题信息
	 */
	@GetMapping("getTestUserResult")
	public BaseResponsePack getTestUserResult(@RequestParam String testId, @RequestParam String userId) {
		QueryWrapper<TestUserResult> wrapper = new QueryWrapper<>();
		wrapper.eq("test_id", testId);
		wrapper.eq("user_id", userId);
		TestUserResult result = testUserResultService.getOne(wrapper);
		if (result == null) {
			return BaseResponsePack.simpleFail("无记录");
		} else {
			return BaseResponsePack.simpleSuccess(result);
		}
	}

	/**
	 * 获取试题
	 */
	@GetMapping("getTestQuestion")
	public BaseResponsePack getTestQuestion(@RequestParam String testId) {
		TestQuestions testQuestions = testQuestionsService.getById(testId);
		if (testQuestions == null) {
			return BaseResponsePack.simpleFail("试卷不存在");
		} else {
			return BaseResponsePack.simpleSuccess(testQuestions);
		}
	}

	/**
	 * 提交答案
	 */
	@PostMapping("submitAnswer")
	public BaseResponsePack submitAnswer(@RequestBody TestUserResult testUserResult) {
		//获取用户答题信息
		QueryWrapper<TestUserResult> wrapper = new QueryWrapper<>();
		wrapper.eq("test_id", testUserResult.getTestId());
		wrapper.eq("user_id", testUserResult.getUserId());
		TestUserResult oldResult = testUserResultService.getOne(wrapper);
		if (oldResult == null) {
			//创建用户答题信息
			testUserResult.setTryTime(1);
			testUserResult.setIsScoring(0);
			testUserResultService.save(testUserResult);
		} else {
			UpdateWrapper<TestUserResult> updateWrapper = new UpdateWrapper<>(oldResult);
			updateWrapper.set("user_select_answer", testUserResult.getUserSelectAnswer());
			updateWrapper.set("user_judge_answer", testUserResult.getUserJudgeAnswer());
			updateWrapper.set("user_complete_answer", testUserResult.getUserCompleteAnswer());
			updateWrapper.set("user_comprehension_answer", testUserResult.getUserComprehensionAnswer());
			updateWrapper.set("try_time", oldResult.getTryTime() + 1);
			updateWrapper.set("is_scoring", 0);
			testUserResultService.update(updateWrapper);
		}
		return BaseResponsePack.simpleSuccess();
	}
}
