package com.kevin.user_service.service.impl;

import com.kevin.user_service.pojo.UserInfo;
import com.kevin.user_service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TelVerifyUserDetailsServiceImpl implements UserDetailsService {

	private UserInfoService userService;

	@Autowired
	public TelVerifyUserDetailsServiceImpl(UserInfoService userService) {
		this.userService = userService;
	}

	/**
	 * 根据手机号查询用户对象
	 */
	@Override
	public UserDetails loadUserByUsername(String tel) throws UsernameNotFoundException {
		// 从数据库查询用户
		UserInfo userInfo = userService.getUser(tel);
		if (userInfo == null) {
			return null;
		}
//
//		// 把用户信息封装到一个 userdetails 对象中，UserDetails是一个接口，LoginUser实现了这个接口
//		LoginUser loginUser = new LoginUser();
//		loginUser.setUser(user);
//		return loginUser;
		return null;
	}
}