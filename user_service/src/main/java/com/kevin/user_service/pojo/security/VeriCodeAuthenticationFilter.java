package com.kevin.user_service.pojo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 认证（请求） 的 Filter：
 * 1. 根据 URL+Method 拦截请求。
 * 2. 提取登录凭证，封装到 Token 中。
 * 3.调用 AuthenticationManager 的 authenticated 方法来认证 Token。
 */
public class VeriCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * 配置拦截的 URL+Method
	 */
	private static final AntPathRequestMatcher VERI_CODE_MATCHER =
			new AntPathRequestMatcher("/login/tel", "POST");

	/**
	 * 请求中提供凭证的参数名
	 */
	private static final String TEL_PARAMETER = "tel";
	private static final String CODE_PARAMETER = "verificationCode";

	public VeriCodeAuthenticationFilter() {
		super(VERI_CODE_MATCHER);
	}

	/**
	 * 用来获取前端传递的手机号和验证码，然后调用 authenticate 方法进行认证
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		//检验请求方法是 POST
		if (!"POST".equals(request.getMethod())) {
			throw new AuthenticationServiceException("请求方式有误: " + request.getMethod());
		}
		//检验请求参数格式是json
		if (!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			throw new AuthenticationServiceException("参数不是json：" + request.getMethod());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
		String tel = map.get(TEL_PARAMETER);
		String verificationCode = map.get(CODE_PARAMETER);
		tel = tel == null ? "" : tel.trim();
		verificationCode = verificationCode == null ? "" : verificationCode.trim();

		// 提取登录凭证，封装到 Token 中。
		VeriCodeAuthenticationToken authRequest = new VeriCodeAuthenticationToken(tel, verificationCode);

		//设置ip、sessionId信息
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, VeriCodeAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}
}