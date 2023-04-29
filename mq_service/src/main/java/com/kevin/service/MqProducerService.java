package com.kevin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.pojo.BaseResponsePack;
import com.kevin.pojo.TestUserResult;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqProducerService {
	public static final String TEST_TOPIC = "test";
	public static final String SUBMIT_TAG = "submit";

	Producer testProducer;

	ClientServiceProvider provider = ClientServiceProvider.loadService();
	ObjectMapper objectMapper = new ObjectMapper();

	public MqProducerService(@Value("${custom.broker-addr}") String endpoint) {
		// 配置 provider 的目的地
		ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
		ClientConfiguration configuration = builder.build();
		// 初始化Producer
		try {
			this.testProducer = provider.newProducerBuilder()
					.setTopics(TEST_TOPIC)
					.setClientConfiguration(configuration)
					.build();
		} catch (ClientException e) {
			throw new RuntimeException(e);
		}
	}

	public BaseResponsePack sendTestRequest(TestUserResult testUserResult) {
		try {
			// 普通消息发送
			Message message = provider.newMessageBuilder()
					.setTopic(TEST_TOPIC)
					// 设置消息Tag，用于消费端根据指定Tag过滤消息。
					.setTag(SUBMIT_TAG)
					// 消息体。
					.setBody(objectMapper.writeValueAsString(testUserResult).getBytes())
					.build();
			// 发送消息，需要关注发送结果，并捕获失败等异常。
			SendReceipt sendReceipt = testProducer.send(message);
			System.out.println("发送成功");
			return BaseResponsePack.simpleSuccess();
		} catch (Exception e) {
			return BaseResponsePack.simpleFail("Failed to send message: " + e.getMessage());
		}
	}
}