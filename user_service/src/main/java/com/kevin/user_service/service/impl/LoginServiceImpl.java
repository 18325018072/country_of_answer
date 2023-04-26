package com.kevin.user_service.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kevin.user_service.pojo.BaseResponsePack;
import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.LoginService;
import com.kevin.user_service.service.UserInfoService;
import com.kevin.user_service.util.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
	private final UserInfoService userInfoService;

	private final RedisTemplate<String, String> redisTemplate;

	DefaultKaptcha defaultKaptcha;

	@Autowired
	public LoginServiceImpl(UserInfoService userInfoService, RedisTemplate<String, String> redisTemplate, DefaultKaptcha defaultKaptcha) {
		this.userInfoService = userInfoService;
		this.redisTemplate = redisTemplate;
		this.defaultKaptcha = defaultKaptcha;
	}

	/**
	 * 手机号码登录-发送验证码
	 */
	@Override
	public void sendVerificationCode(String tel) {
		// 生成文字验证码
//toChange 测试数据：		String verificationCode = defaultKaptcha.createText();
		String verificationCode = "1111";
		//发送至手机
		//保存至redis:5分钟后失效
		redisTemplate.opsForValue().set(StringConstant.REDIS_VERIFICATION_CODE_PREFIX + tel, verificationCode, 5, TimeUnit.MINUTES);
	}

	/**
	 * 手机号码登录-验证code匹配
	 *
	 * @param tel              手机号码
	 * @param verificationCode 验证码
	 * @return 如果匹配，BaseResponsePack包含 UserInfo
	 */
	@Override
	public BaseResponsePack login(String tel, String verificationCode) {
		//向redis验证
		if (verificationCode.equals(redisTemplate.opsForValue().get(StringConstant.REDIS_VERIFICATION_CODE_PREFIX + tel))) {
			UserInfo user = userInfoService.getUser(tel);
			//如果没注册，则自动注册
			if (user == null) {
				System.out.println("自动注册" + tel);
				userInfoService.register(tel);
			}
			user = userInfoService.getUser(tel);
			//删除redis验证码
			redisTemplate.delete(StringConstant.REDIS_VERIFICATION_CODE_PREFIX + tel);
			//返回登录用户信息
			return BaseResponsePack.simpleSuccess(user);
		} else {
			//验证码错误
			return BaseResponsePack.simpleFail("验证码错误");
		}
	}
}
