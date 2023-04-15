package com.kevin.test_service.service;

/**
 * 定时任务
 */
public interface TestScheduleTask {
	/**
	 * 每小时刷新热门试卷
	 */
	void flushHotTest();
}
