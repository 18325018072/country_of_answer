package com.kevin.test_service.service.impl;

import com.kevin.test_service.pojo.TestInfo;
import com.kevin.test_service.service.HotTestService;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.util.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 20349
 * @description 针对表【hot_test(热门试卷推荐)】的数据库操作Service实现
 * @createDate 2023-04-12 17:28:33
 */
@Service
public class HotTestServiceImpl implements HotTestService {

	TestInfoService testInfoService;
	RedisTemplate<String, String> redisTemplate;

	@Autowired
	public HotTestServiceImpl(TestInfoService testInfoService, RedisTemplate<String, String> redisTemplate) {
		this.testInfoService = testInfoService;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取热门测试。
	 * 热门排名从 redis 获取。
	 * 这种数据的数组：{testId: '1523', testName: '微积分练习题', difficulty: 'middle', publisher: '高老师', studyNum: '15234'}
	 */
	@Override
	public List<TestInfo> getHotTestInfo() {
		//从 redis 查询热门试卷
		Long size = redisTemplate.opsForList().size(StringConstant.REDIS_HOT_TEST_LIST);
		if (size == null) {
			return null;
		}
		List<String> hotTestIdList = redisTemplate.opsForList().leftPop(StringConstant.REDIS_HOT_TEST_LIST, size);
		if (hotTestIdList == null) {
			return null;
		}
		//从数据库获取试卷信息
		List<TestInfo> hotTestList = new ArrayList<>();
		for (String hotTestId : hotTestIdList) {
			hotTestList.add(testInfoService.getById(hotTestId));
		}
		return hotTestList;
	}
}




