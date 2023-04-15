package com.kevin.test_service.service;

import com.kevin.test_service.pojo.TestInfo;

import java.util.List;

/**
 * @author 20349
 * @description 针对表【hot_test(热门试卷推荐)】的数据库操作Service
 * @createDate 2023-04-12 17:28:33
 */
public interface HotTestService {
	/**
	 * 获取热门测试
	 */
	List<TestInfo> getHotTestInfo();
}
