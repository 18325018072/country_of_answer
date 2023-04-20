package com.kevin.user_service.security;

import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import com.kevin.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	UserInfoService userInfoService;
	private JwtUtil jwtUtil;

	@Autowired
	public JwtAuthenticationTokenFilter(JwtUtil jwtUtil, UserInfoService userInfoService) {
		this.jwtUtil = jwtUtil;
		this.userInfoService = userInfoService;
	}

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//提取 请求头中的token信息
		String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		//判断 是否存在token
		if (requestHeader == null || !requestHeader.startsWith(tokenHead)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 去除字段名称, 获取真正 jwt
		String jwt = requestHeader.substring(tokenHead.length());
		// 获取 token 中的用户信息
		UserInfo userInfoFromJwt = jwtUtil.parseJwt(jwt);
		Integer userId = userInfoFromJwt.getUserId();
		// token存在用户但未登陆
		// SecurityContextHolder.getContext().getAuthentication() 获取上下文对象中认证信息
		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// 自定义数据源获取用户信息
			UserInfo userInfoFromService = userInfoService.getById(userId);
			// 验证token是否有效 验证token用户名和存储的用户名是否一致以及是否在有效期内, 重新设置用户对象
			if (userInfoFromService.equals(userInfoFromJwt)) {
				// 重新将用户信息封装到UsernamePasswordAuthenticationToken
				VeriCodeAuthenticationToken token = new VeriCodeAuthenticationToken(userInfoFromService.getTel(), null);
				token.setDetails(userInfoFromService);
				token.setAuthenticated(true);
				// 将信息存入上下文对象
				SecurityContextHolder.getContext().setAuthentication(token);
				filterChain.doFilter(request, response);
			} else {
				response.getWriter().write("invalid token");
			}
		}
	}
}