package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.BaseResponsePack;
import pojo.TestUserResult;
import service.MqProducerService;

@RequestMapping("/test")
@RestController
public class TestMqController {
	MqProducerService mqProducerService;

	@Autowired
	public TestMqController(MqProducerService mqProducerService) {
		this.mqProducerService = mqProducerService;
	}

	/**
	 * 提交答案
	 */
	@PostMapping("submitAnswer")
	public BaseResponsePack submitAnswer(@RequestBody TestUserResult testUserResult) {
		return mqProducerService.sendTestRequest(testUserResult);
	}
}
