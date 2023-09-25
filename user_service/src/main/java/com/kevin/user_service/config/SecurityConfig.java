package com.kevin.user_service.config;

import com.kevin.user_service.security.JwtAuthenticationTokenFilter;
import com.kevin.user_service.security.VeriCodeAuthenticationFilter;
import com.kevin.user_service.security.VeriCodeAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * 定义SpringSecurity不需要拦截的url
	 */
	private static final String[] URL_ACCESS_WHITELISTS = {"/user/verify", "/user/login"};

	/**
	 * 配置 HttpSecurity:访问限制、登录、登出
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, VeriCodeAuthenticationFilter veriCodeAuthenticationFilter, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) throws Exception {
		return httpSecurity.headers().cacheControl().and()
				.and().authorizeRequests()
				//允许所有人请求验证码和登录
				.antMatchers(URL_ACCESS_WHITELISTS).permitAll()
				//除了”请求验证码和登录“外，其他请求都需要认证
				.anyRequest().authenticated()
				//禁用session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().csrf().disable()
				//拦截器链中，把 手机号认证过滤器 加到 UsernamePasswordAuthenticationFilter 之后
				.addFilterAfter(veriCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(veriCodeAuthenticationProvider)
				//设置未登录的响应
				.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(401);
					response.setContentType("text/html;charset=utf-8");
					PrintWriter writer = response.getWriter();
					writer.write("UnAuthorized: " + authException.getMessage());
					writer.close();
				})
				.and().build();
	}

	/**
	 * Manager
	 */
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, VeriCodeAuthenticationProvider veriCodeAuthenticationProvider) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(veriCodeAuthenticationProvider).build();
	}
}