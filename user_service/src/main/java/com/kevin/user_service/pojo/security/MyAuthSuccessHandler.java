package com.kevin.user_service.pojo.security;

import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import com.kevin.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功处理器：在响应头添加 JWT
 */
@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {
	JwtUtil jwtUtil;
	UserInfoService userInfoService;

	@Autowired
	public MyAuthSuccessHandler(JwtUtil jwtUtil, UserInfoService userInfoService) {
		this.jwtUtil = jwtUtil;
		this.userInfoService = userInfoService;
	}


	/**
	 * 当用户已成功通过身份验证时调用：在响应头添加 JWT
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		UserInfo user = userInfoService.getUser(((VeriCodeAuthenticationToken) authentication).getPrincipal());
		String jwtToken = jwtUtil.createJwt(user);
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
	}
}