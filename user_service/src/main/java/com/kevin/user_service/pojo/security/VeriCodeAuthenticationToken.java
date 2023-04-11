package com.kevin.user_service.pojo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 过滤器 从请求中 提取凭证，封装到 Token 中，用于给 Manager 认证。
 */
public class VeriCodeAuthenticationToken extends AbstractAuthenticationToken {
	/**
	 * 手机号
	 */
	private final String tel;

	/**
	 * 验证码
	 */
	private String verificationCode;


	public VeriCodeAuthenticationToken(String tel, String verificationCode) {
		super(null);
		this.tel = tel;
		this.verificationCode = verificationCode;
		setAuthenticated(false);
	}

	public VeriCodeAuthenticationToken(String tel, String verificationCode, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.tel = tel;
		this.verificationCode = verificationCode;
		super.setAuthenticated(true);
	}

	@Override
	public String getCredentials() {
		return verificationCode;
	}

	@Override
	public String getPrincipal() {
		return tel;
	}
}