package com.kevin.user_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.user_service.pojo.security.VeriCodeAuthenticationFilter;
import com.kevin.user_service.pojo.security.VeriCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private VeriCodeAuthenticationProvider veriCodeAuthenticationProvider;

	/**
	 * 定义SpringSecurity不需要拦截的url
	 */
	private static final String[] URL_ACCESS_WHITELISTS = {"/verify", "/login"};

	@Autowired
	public SecurityConfig(VeriCodeAuthenticationProvider veriCodeAuthenticationProvider) {
		this.veriCodeAuthenticationProvider = veriCodeAuthenticationProvider;
	}

	/**
	 * 密码加密算法
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 配置 HttpSecurity:访问限制、登录、登出
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeRequests()
				//允许所有人请求验证码和登录
				.antMatchers(URL_ACCESS_WHITELISTS).permitAll()
				//除了”请求验证码和登录“外，其他请求都需要认证
				.anyRequest().authenticated()
				.and().csrf().disable()
				.cors()
				.configurationSource(corsConfigurationSource())
				//拦截器链中，把 手机号认证过滤器 加到 UsernamePasswordAuthenticationFilter 之后
				.and().addFilterAfter(veriCodeAuthenticationFilter(httpSecurity), UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(veriCodeAuthenticationProvider)
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
	 * 验证码-filter
	 */
	@Bean
	public VeriCodeAuthenticationFilter veriCodeAuthenticationFilter(HttpSecurity httpSecurity) throws Exception {
		//Manager
		AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(veriCodeAuthenticationProvider).build();
		//Filter
		VeriCodeAuthenticationFilter filter = new VeriCodeAuthenticationFilter();
		//认证使用
		filter.setAuthenticationManager(authenticationManager);
		//设置登陆成功返回值是json
		filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(out, authentication);
			out.close();
		});
		//设置登陆失败返回值是json
		filter.setAuthenticationFailureHandler((request, response, exception) -> {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			Map<String, String> map = new HashMap<>(1);
			map.put("errMsg", "手机登陆失败：" + exception.getMessage());
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(out, map);
			out.close();
		});
		return filter;
	}
}