package com.kevin.test_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.service.TestScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class TestScheduleTaskImpl implements TestScheduleTask {
	/**
	 * redis中热门试卷存储的list名
	 */
	public static final String REDIS_HOT_TEST_LIST = "hot_test_id_list";

	/**
	 * 展示热门试卷的最大个数
	 */
	public static final int HOT_TEST_MAX_NUM = 10;
	RedisTemplate<String, String> redisTemplate;
	TestInfoService testInfoService;

	@Autowired
	public TestScheduleTaskImpl(RedisTemplate<String, String> redisTemplate, TestInfoService testInfoService) {
		this.redisTemplate = redisTemplate;
		this.testInfoService = testInfoService;
	}

	/**
	 * 每小时刷新热门试卷：从数据库读取，保存到 redis list中。取的时候从右边 pop。
	 */
	@Override
	@Scheduled(cron = "* * 0/1 * * ? ")
	public void flushHotTest() {
		QueryWrapper<TestInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("study_num");
		queryWrapper.last("limit " + HOT_TEST_MAX_NUM);
		List<TestInfo> list = testInfoService.list(queryWrapper);
		//创建新的list
		for (TestInfo testInfo : list) {
			redisTemplate.opsForList().leftPush("temp_" + REDIS_HOT_TEST_LIST, testInfo.getTestId() + "");
		}
		//替换旧的list
		redisTemplate.rename("temp_" + REDIS_HOT_TEST_LIST, REDIS_HOT_TEST_LIST);
	}
}
