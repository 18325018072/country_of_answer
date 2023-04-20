package com.kevin.user_service.security;

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
import java.io.IOException;

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
	 * 当用户已成功通过身份验证时调用：在响应头添加 JWT；在响应体添加 userID、Name
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		//暴露响应头的AUTHORIZATION字段
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
		//在响应头添加 jwt
		UserInfo user = userInfoService.getUser(((VeriCodeAuthenticationToken) authentication).getPrincipal());
		String jwtToken = jwtUtil.createJwt(user);
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
//		//在响应体添加用户id和用户名
//		ObjectMapper objectMapper = new ObjectMapper();
//		Map<String,String> map=new HashMap<>(2);
//		map.put("userId", String.valueOf(user.getUserId()));
//		map.put("userName",user.getUserName());
//		PrintWriter writer = response.getWriter();
//		objectMapper.writeValue(writer,map);
//		writer.close();
//		//设置响应体为json类型
//		response.setContentType("application/json; charset=utf-8");
	}
}