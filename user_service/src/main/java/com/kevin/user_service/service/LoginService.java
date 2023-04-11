package com.kevin.user_service.service;

import com.kevin.user_service.pojo.BaseResponsePack;

import java.io.IOException;

public interface LoginService {
	/**
	 * 手机号码登录-发送验证码
	 */
	void sendVerificationCode(String tel) throws IOException;

	/**
	 * 手机号码登录-验证code匹配
	 *
	 * @param tel              手机号码
	 * @param verificationCode 验证码
	 * @return 是否匹配
	 */
	BaseResponsePack login(String tel, String verificationCode);
}
