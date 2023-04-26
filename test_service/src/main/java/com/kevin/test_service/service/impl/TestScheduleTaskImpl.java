package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.service.TestScheduleTask;
import com.kevin.test_service.util.IntegerConstant;
import com.kevin.test_service.util.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class TestScheduleTaskImpl implements TestScheduleTask {

	RedisTemplate<String, String> redisTemplate;
	TestInfoService testInfoService;
	String hotListName;

	@Autowired
	public TestScheduleTaskImpl(RedisTemplate<String, String> redisTemplate, TestInfoService testInfoService) {
		this.redisTemplate = redisTemplate;
		this.testInfoService = testInfoService;
		hotListName = StringConstant.REDIS_HOT_TEST_LIST;
		//启动服务器时立刻刷新一次
		flushHotTest();
	}

	/**
	 * 每小时刷新热门试卷：从数据库读取，保存到 redis list中。取的时候从右边 pop。
	 */
	@Override
	@Scheduled(cron = "* * 0/1 * * ? ")
	public void flushHotTest() {
		QueryWrapper<TestInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("study_num");
		queryWrapper.last("limit " + IntegerConstant.HOT_TEST_MAX_NUM);
		List<TestInfo> list = testInfoService.list(queryWrapper);
		//创建新的list
		for (TestInfo testInfo : list) {
			redisTemplate.opsForList().leftPush("temp_" + hotListName, testInfo.getTestId() + "");
		}
		//替换旧的list
		redisTemplate.rename("temp_" + hotListName, hotListName);
	}
}
