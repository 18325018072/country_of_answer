package com.kevin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.pojo.BaseResponsePack;
import com.kevin.pojo.TestUserResult;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqProducerService {
	private RocketMQTemplate rocketMqTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public MqProducerService(RocketMQTemplate rocketMqTemplate) {
		this.rocketMqTemplate = rocketMqTemplate;
	}

	/**
	 * 同步的方式发送
	 */
	public BaseResponsePack sendTestRequest(TestUserResult testUserResult) {
		try {
			SendResult sendResult = rocketMqTemplate.syncSend("test:submit", objectMapper.writeValueAsString(testUserResult));
			if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
				System.out.println("发送成功");
				return BaseResponsePack.simpleSuccess();
			} else {
				System.out.println("发送失败");
				return BaseResponsePack.simpleFail(String.valueOf(sendResult.getSendStatus()));
			}
		} catch (Exception e) {
			return BaseResponsePack.simpleFail("发送异常: " + e.getMessage());
		}
	}

	/**
	 * 	public static final String TEST_TOPIC = "test";
	 * 	public static final String SUBMIT_TAG = "submit";
	 *
	 * 	Producer testProducer;
	 *
	 * 	ClientServiceProvider provider = ClientServiceProvider.loadService();
	 * 	ObjectMapper objectMapper = new ObjectMapper();
	 *
	 * 	public MqProducerService(@Value("${custom.broker-addr}") String endpoint) {
	 * 		// 配置 provider 的目的地
	 * 		ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
	 * 		ClientConfiguration configuration = builder.build();
	 * 		// 初始化Producer
	 * 		try {
	 * 			this.testProducer = provider.newProducerBuilder()
	 * 					.setTopics(TEST_TOPIC)
	 * 					.setClientConfiguration(configuration)
	 * 					.build();
	 *                } catch (ClientException e) {
	 * 			throw new RuntimeException(e);
	 *        }* 	}
	 */
}