package com.kevin.test_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.test_service.pojo.TestUserResult;
import com.kevin.test_service.service.TestUserResultService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(consumerGroup = "testSubmitConsumerGroup", topic = "test", selectorExpression = "submit")
public class TestSubmitService implements RocketMQListener<String> {
	TestUserResultService testUserResultService;
	public ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public TestSubmitService(TestUserResultService testUserResultService) {
		this.testUserResultService = testUserResultService;
	}

	@Override
	public void onMessage(String s) {
		try {
			TestUserResult result = objectMapper.readValue(s, TestUserResult.class);
			//tochange 测试
			testUserResultService.testSubmitAnswer(result);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

//
//@Component
//public class TestSubmitService {
//	TestUserResultService testUserResultService;
//	public static final String TEST_TOPIC = "test";
//	public static final String SUBMIT_TAG = "submit";
//	public ObjectMapper objectMapper = new ObjectMapper();
//
//	PushConsumer pushConsumer;
//
//	@Autowired
//	public TestSubmitService(TestUserResultService testUserResultService, @Value("${custom.broker-addr}") String endpoints) {
//		System.out.println("TestSubmitService init");
//		this.testUserResultService = testUserResultService;
//		// 配置Consumer
//		ClientServiceProvider provider = ClientServiceProvider.loadService();
//		ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
//				.setEndpoints(endpoints)
//				.build();
//		FilterExpression filterExpression = new FilterExpression(SUBMIT_TAG, FilterExpressionType.TAG);
//		// 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
//		try {
//			this.pushConsumer = provider.newPushConsumerBuilder()
//					.setClientConfiguration(clientConfiguration)
//					// 设置预绑定的订阅关系。
//					.setSubscriptionExpressions(Collections.singletonMap(TEST_TOPIC, filterExpression))
//					.setConsumerGroup("testSubmitConsumerGroup")
//					// 设置消费监听器。
//					.setMessageListener(messageView -> {
//						// 处理消息并返回消费结果。
//						try {
//							TestUserResult result = objectMapper.readValue(messageView.getBody().array(), TestUserResult.class);
//							System.out.println("接收成功" + result);
//							//tochange 测试
//							testUserResultService.testSubmitAnswer(result);
//							return ConsumeResult.SUCCESS;
//						} catch (Exception e) {
//							return ConsumeResult.FAILURE;
//						}
//					})
//					.build();
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage());
//		}
//
//	}
//}
