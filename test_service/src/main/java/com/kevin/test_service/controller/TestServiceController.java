package com.kevin.test_service.controller;

import com.kevin.test_service.pojo.BaseResponsePack;
import com.kevin.test_service.service.HotTestService;
import com.kevin.test_service.service.TestAnswerService;
import com.kevin.test_service.service.TestInfoService;
import com.kevin.test_service.service.TestQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestServiceController {
	HotTestService hotTestService;

	@Autowired
	public TestServiceController(HotTestService hotTestService) {
		this.hotTestService = hotTestService;
	}

	/**
	 * 获取热门试卷
	 */
	@GetMapping("hotTest")
	public BaseResponsePack getHotTest() {
		return BaseResponsePack.simpleSuccess(hotTestService.getHotTestInfo());
	}
}
