package com.kevin.user_service.pojo.security;

import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import com.kevin.user_service.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 实现认证
 */
@Component
public class VeriCodeAuthenticationProvider implements AuthenticationProvider {

	UserInfoService userInfoService;

	RedisTemplate<String, String> redisTemplate;

	@Autowired
	public VeriCodeAuthenticationProvider(UserInfoService userInfoService, RedisTemplate<String, String> redisTemplate) {
		this.userInfoService = userInfoService;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 手机号、验证码的认证
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		VeriCodeAuthenticationToken token = (VeriCodeAuthenticationToken) authentication;
		String tel = token.getPrincipal();
		String tokenVerificationCode = token.getCredentials();
		//1.从 redis 中获取验证码
		String redisVerificationCode = redisTemplate.opsForValue().get(LoginServiceImpl.VERIFICATION_CODE_PREFIX + tel);
		if (redisVerificationCode == null) {
			throw new BadCredentialsException("验证码已经过期，请重新发送验证码");
		} else if (!redisVerificationCode.equals(tokenVerificationCode)) {
			throw new BadCredentialsException("验证码不正确");
		}
		//2.认证成功
		//根据手机号查询用户信息
		UserInfo user = userInfoService.getUser(tel);
		//无该用户，则自动创建
		if (user == null) {
			userInfoService.register(tel);
		}
		((VeriCodeAuthenticationToken) authentication).setDetails(user);
		authentication.setAuthenticated(true);
		return authentication;
	}

	/**
	 * 判断是否支持认证该类型的 Authentication 对象。
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// 如果参数是 PhoneNumAuthenticationToken 类型，返回true
		return (VeriCodeAuthenticationToken.class.isAssignableFrom(authentication));
	}
}