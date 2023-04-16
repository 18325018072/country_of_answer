package com.kevin.user_service.config;

import com.kevin.user_service.pojo.security.MyAuthSuccessHandler;
import com.kevin.user_service.pojo.security.VeriCodeAuthenticationFilter;
import com.kevin.user_service.pojo.security.VeriCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	MyAuthSuccessHandler myAuthSuccessHandler;

	HttpSecurity httpSecurity;

	private VeriCodeAuthenticationProvider veriCodeAuthenticationProvider;

	/**
	 * 定义SpringSecurity不需要拦截的url
	 */
	private static final String[] URL_ACCESS_WHITELISTS = {"/verify", "/login"};

	@Autowired
	public SecurityConfig(MyAuthSuccessHandler myAuthSuccessHandler, HttpSecurity httpSecurity, VeriCodeAuthenticationProvider veriCodeAuthenticationProvider) {
		this.myAuthSuccessHandler = myAuthSuccessHandler;
		this.httpSecurity = httpSecurity;
		this.veriCodeAuthenticationProvider = veriCodeAuthenticationProvider;
	}

	/**
	 * 配置 HttpSecurity:访问限制、登录、登出
	 */
	@Bean
	public SecurityFilterChain filterChain() throws Exception {
		return httpSecurity.authorizeRequests()
				//允许所有人请求验证码和登录
				.antMatchers(URL_ACCESS_WHITELISTS).permitAll()
				//除了”请求验证码和登录“外，其他请求都需要认证
				.anyRequest().authenticated()
				//禁用session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//解决 CORS 问题
				.and().cors().configurationSource(corsConfigurationSource())
				.and().csrf().disable()
				//拦截器链中，把 手机号认证过滤器 加到 UsernamePasswordAuthenticationFilter 之后
				.addFilterAfter(veriCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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
	 * 解决 cors 同源问题
	 */
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Collections.singletonList("*"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}


	/**
	 * filter
	 */
	@Bean
	public VeriCodeAuthenticationFilter veriCodeAuthenticationFilter() throws Exception {
		VeriCodeAuthenticationFilter filter = new VeriCodeAuthenticationFilter();
		//认证使用
		filter.setAuthenticationManager(authenticationManager());
		//设置登陆成功，返回json
		filter.setAuthenticationSuccessHandler(myAuthSuccessHandler);
		//设置登陆失败返回值是json
		filter.setAuthenticationFailureHandler((request, response, exception) -> {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("手机登陆失败：" + exception.getMessage());
			writer.close();
		});
		return filter;
	}

	/**
	 * Manager
	 */
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(veriCodeAuthenticationProvider).build();
	}
}