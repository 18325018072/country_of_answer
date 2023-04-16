package com.kevin.user_service.pojo.security;

import com.kevin.user_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//提取 请求头中的token信息
		String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		//判断 是否存在token
		if (null != requestHeader && requestHeader.startsWith(tokenHead)) {
			// 去除字段名称, 获取真正token
			String jwt = requestHeader.substring(tokenHead.length());
			// 利用token获取用户名
			Claims claims = jwtUtil.parseJwt(jwt);
			String username = jwtUtil.getUserNameFromToken(jwt);
			// token存在用户但未登陆
			// SecurityContextHolder.getContext().getAuthentication() 获取上下文对象中认证信息
			if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
				// 自定义数据源获取用户信息
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// 验证token是否有效 验证token用户名和存储的用户名是否一致以及是否在有效期内, 重新设置用户对象
				if (jwtUtil.validateToken(jwt, userDetails)) {
					// 重新将用户信息封装到UsernamePasswordAuthenticationToken
					UsernamePasswordAuthenticationToken authenticationToken = new
							UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// 将信息存入上下文对象
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
