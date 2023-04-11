package com.kevin.user_service.controller;


import com.kevin.user_service.pojo.BaseResponsePack;
import com.kevin.user_service.service.LoginService;
import com.kevin.user_service.service.UserInfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Data
public class UserServiceController {
	/**
	 * 手机号码正则
	 */
	private final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
	private LoginService loginService;

	private UserInfoService userInfoService;

	@Autowired
	public UserServiceController(LoginService loginService, UserInfoService userInfoService) {
		this.loginService = loginService;
		this.userInfoService = userInfoService;
	}

	/**
	 * 通过手机号码登录-验证手机号码，并发送验证码。
	 * 登录通过 SpringSecurity 完成。
	 */
	@GetMapping("verify")
	public BaseResponsePack getVerify(@RequestParam String tel) {
		//验证手机号码
		if (!tel.matches(PHONE_REGEX)) {
			return BaseResponsePack.simpleFail("请输入合法的电话号码");
		} else {
			//发送验证码
			try {
				loginService.sendVerificationCode(tel);
				return BaseResponsePack.simpleSuccess();
			} catch (Exception e) {
				return BaseResponsePack.simpleFail("发送验证码异常:" + e.getMessage());
			}
		}
	}

	@GetMapping("test")
	public BaseResponsePack test(@RequestParam String num) {
		return BaseResponsePack.simpleFail(num + 1);
	}
}
